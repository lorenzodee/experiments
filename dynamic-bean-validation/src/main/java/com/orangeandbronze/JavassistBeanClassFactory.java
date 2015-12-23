package com.orangeandbronze;

import java.io.Serializable;
import java.util.Map;
import java.util.Map.Entry;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtField;
import javassist.CtMethod;
import javassist.NotFoundException;

public class JavassistBeanClassFactory implements BeanClassFactory {

	private static final String SETTER_PREFIX = "set";
	private static final String GETTER_PREFIX = "get";

	private final ClassPool pool;

	public JavassistBeanClassFactory() {
		this(ClassPool.getDefault());
	}

	public JavassistBeanClassFactory(ClassPool pool) {
		if (pool == null) {
			throw new IllegalArgumentException("Pool cannot be null");
		}
		this.pool = pool;
	}

	@Override
	public Class<?> createBeanClass(String className, Map<String, Class<?>> properties)
			throws NotFoundException, CannotCompileException {
		CtClass cc;
		try {
			cc = pool.getCtClass(className);
			return cc.toClass();
		} catch (NotFoundException e) {
			cc = pool.makeClass(className);
		}

		// add this to define a super class to extend
		// cc.setSuperclass(resolveCtClass(MySuperClass.class));

		// add this to define an interface to implement
		cc.addInterface(resolveCtClass(Serializable.class));

		for (Entry<String, Class<?>> entry : properties.entrySet()) {
			cc.addField(new CtField(resolveCtClass(entry.getValue()), entry.getKey(), cc));
			// add getter
			cc.addMethod(generateGetter(cc, entry.getKey(), entry.getValue()));
			// add setter
			cc.addMethod(generateSetter(cc, entry.getKey(), entry.getValue()));
		}

		// cc.detach();
		// cc.freeze();
		// cc.defrost();

		// TODO Remember all generated CtClass objects and detach when shutting down

		return cc.toClass();
	}

	private String capitalizeFirstCharacter(String str) {
		if (str == null || str.length() == 0) {
			return str;
		}
		StringBuilder sb = new StringBuilder(str.length());
		sb.append(Character.toUpperCase(str.charAt(0)));
		sb.append(str.substring(1));
		return sb.toString();
	}

	private CtMethod generateGetter(
			CtClass declaringClass, String fieldName, Class<?> fieldClass)
			throws CannotCompileException {
		String getterName = GETTER_PREFIX + capitalizeFirstCharacter(fieldName);

		StringBuffer sb = new StringBuffer();
		sb.append("public ").append(fieldClass.getName()).append(" ").append(getterName)
				.append("(){")
				.append("return this.").append(fieldName).append(";")
				.append("}");
		return CtMethod.make(sb.toString(), declaringClass);
	}

	private CtMethod generateSetter(
			CtClass declaringClass, String fieldName, Class<?> fieldClass)
			throws CannotCompileException {
		String setterName = SETTER_PREFIX + capitalizeFirstCharacter(fieldName);

		StringBuffer sb = new StringBuffer();
		sb.append("public void ").append(setterName)
				.append("(").append(fieldClass.getName()).append(" ")
				.append(fieldName).append(")").append("{")
				.append("this.").append(fieldName).append("=")
					.append(fieldName).append(";")
				.append("}");
		return CtMethod.make(sb.toString(), declaringClass);
	}

	private CtClass resolveCtClass(Class<?> clazz) throws NotFoundException {
		ClassPool pool = ClassPool.getDefault();
		return pool.get(clazz.getName());
	}

}
