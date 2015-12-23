package com.orangeandbronze;

import java.util.Map;

/**
 * Strategy interface for creating JavaBean classes at runtime.
 */
public interface BeanClassFactory {

	/**
	 * /** Creates a JavaBean class with the given class name and properties.
	 * The properties will have getter and setter methods following the JavaBean
	 * conventions.
	 * 
	 * @param className
	 *            the given class name
	 * @param properties
	 *            map of property name-and-type pairs
	 * @return the created JavaBean class
	 * @throws Exception
	 *             when something goes wrong
	 */
	Class<?> createBeanClass(
			String className, Map<String, Class<?>> properties) throws Exception;

	// TODO createBeanClass with a specified super class

	// TODO createBeanClass with a specified interfaces

}
