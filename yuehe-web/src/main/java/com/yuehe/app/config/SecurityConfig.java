package com.yuehe.app.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

/**
 *
 */

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private AuthenticationSuccessHandler authenticationSuccessHandler;
	@Autowired
	private BeanConfigurations beanConfigurations;

	/**
	 * 
	 * 验证登录用户
	 */
	@Autowired
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		 auth.authenticationProvider(beanConfigurations.authenticationProvider());
		//auth.userDetailsService(securityUserDetailsService).passwordEncoder(passwordEncoder);
		// 给内存加载用户
		//auth.inMemoryAuthentication().withUser("user").password("user").roles("USER").and().withUser("admin")
			//	.password("admin").roles("ADMIN");

	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/resources/**", "/webjars/**", "/js/**", "/images/**", "/css/**", "/h2-console/*",
				"/assets/*");
	}

	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		// 关闭X-Frame-Options
		httpSecurity.formLogin()
           .and()
           .logout()
           .permitAll()
           .and()
           .authorizeRequests()
               .antMatchers("/**").hasRole("ADMIN");
               //.antMatchers("/user/**").hasRole("USER");
//		httpSecurity.authorizeRequests().antMatchers("/", "/home").permitAll().anyRequest().authenticated().and()
//				.formLogin().loginPage("/login").defaultSuccessUrl("/").failureUrl("/login?error").permitAll().and()
//				.logout().permitAll();
//		  httpSecurity.authorizeRequests()
//          .antMatchers("/","/home", "/css/**", "/js/**", "/images/**").permitAll()
//          .antMatchers("/user/**").hasAnyRole("USER", "ADMIN")
//          .antMatchers("/admin/**").hasRole("ADMIN")
//          .anyRequest()
//          .authenticated()
//          .and()
//          .formLogin()
//          .loginPage("/")
//          .loginProcessingUrl("/login")
//          .failureUrl("/?login_error")
//          .successHandler(authenticationSuccessHandler);
	}
	

	  
}