package com.taiji.manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication().withUser("user1").password("123").roles("ADMIN")
		.and().withUser("user2").password("123").roles("USER")
		.and().withUser("user3").password("123").roles("ADMIN");
	}
	
	protected void configure(HttpSecurity http) throws Exception {
		//默认登录页面
		http.authorizeRequests()
			.antMatchers("/webjars/**", "/signup", "/about", "/getPage").permitAll()
			.anyRequest().authenticated()
			.and().formLogin()
			.and().httpBasic();
		//自定义页面
		http.csrf().disable();
//		http.authorizeRequests()
//			.antMatchers("/webjars/**", "/signup", "/about").permitAll()
//			.anyRequest().authenticated()
//			.and().formLogin().loginPage("/login").permitAll()
//			.and().logout().logoutSuccessUrl("/login").permitAll();
//		http
//		.authorizeRequests()
//		.antMatchers("/webjars/**", "/signup", "/about").permitAll()
//		.antMatchers("/admin/**").hasRole("UADMINSER")
//		.antMatchers("/db/**").access("hasRole('ADMIN') and hasRole('DBA')")
//		.anyRequest().authenticated().and().formLogin().loginPage("/login").permitAll()
//		.and().logout()
//		.and().httpBasic();
		
	}
	
}
