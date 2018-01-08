package com.alok.spring.rest.test.demo.authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import com.alok.spring.rest.test.demo.entity.ACUser;
import com.alok.spring.rest.test.demo.jpa.ACUserRepository;

@Configuration

//Apparently, it's to swtich off the default web application security configuration and add your own.
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	ACUserRepository acuserRepository;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {

		for (ACUser user : acuserRepository.findAll()) {
			System.out.println("Alok::SecurityConfig:: user: " +  user.getUserName() + ", roles: " + user.getRoles());
			auth.inMemoryAuthentication()
				.withUser(user.getUserName())
				.password(user.getPassword())
				.roles(user.getRoles());
		}

		/*
		 * auth.inMemoryAuthentication().withUser("user1").password("secret1").
		 * roles("USER").and().withUser("user2")
		 * .password("secret2").roles("USER");
		 */
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().anyRequest().fullyAuthenticated();
		http.httpBasic();
		http.csrf().disable();
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring()
				// Spring Security should completely ignore URLs starting with
				// /help
				.antMatchers("/help");

		web.debug(true);
	}
}
