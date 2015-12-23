package makerchecker.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AdviceMode;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableLoadTimeWeaving;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

@Configuration
@EnableLoadTimeWeaving
@EnableGlobalMethodSecurity(prePostEnabled=true, mode=AdviceMode.ASPECTJ)
public class MakerCheckerTestsConfig {

	@Autowired
	public void registerGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication();
	}

}
