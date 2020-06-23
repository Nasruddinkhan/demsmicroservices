/**Jun 23, 2020
 * nasru - SecurityConfig.java
 */
package com.microserices.api.getway.security;

import org.apache.http.client.methods.HttpPost;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

/**
 * @author nasru
 *
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	private Environment env;
	
	public SecurityConfig(final Environment env) {
		super();
		this.env = env;
	}

	@Override
	protected void configure(HttpSecurity  http) throws Exception {
		http.csrf().disable();
		http.headers().frameOptions().disable();
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		http.authorizeRequests() 
			.antMatchers(HttpMethod.POST, env.getProperty("api.signin.path")).permitAll()
			.antMatchers(HttpMethod.POST, env.getProperty("api.signup.path")).permitAll()
			.anyRequest().authenticated()
			.and()
			.addFilter(new AuthorizationFIlter(authenticationManager(), env));
	}
}