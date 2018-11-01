package com.example.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.example.demo.security.AuthAccessDeniedHandler;
import com.example.demo.security.AuthFailureHandler;
import com.example.demo.security.AuthSuccessHandler;
import com.example.demo.security.CustomAccessDecisionManager;
import com.example.demo.security.CustomSecurityMetadataSource;
import com.example.demo.service.AccountService;

/**
 * spring security配置
 * @author lizuodu
 * @date   2018年10月30日
 */
@Configuration
@EnableWebSecurity
@Order(-1)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	// @Autowired private DataSource dataSource;

	@Autowired
	private AccountService accountService;
	
	@Autowired
	private AuthFailureHandler authFailureHandler;
	
	@Autowired
	private AuthSuccessHandler authSuccessHandler;
	
	@Autowired
	private CustomAccessDecisionManager customAccessDecisionManager;
	
	@Autowired
	private CustomSecurityMetadataSource customSecurityMetadataSource;
	
	@Autowired
	private AuthAccessDeniedHandler authAccessDeniedHandler;
	
	
	/**
	 * 不受权限控制url规则
	 */
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/");
		web.ignoring().antMatchers("/**/*.ico");
		web.ignoring().antMatchers("/resource/**/*.*");
		web.ignoring().antMatchers("/login.html");
		web.ignoring().antMatchers("/public/**/*.*");
		web.ignoring().antMatchers("/permission/get");
	}

	/**
	 * 授权规则
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
				.authorizeRequests()
		        .and()
		        .csrf().disable()
		    .headers()
		    	.frameOptions()
		    	.disable()
		    	.and()
		    .formLogin()
		    	.loginPage("/login.html")
		    	.loginProcessingUrl("/login")
		        .usernameParameter("username")
		        .passwordParameter("password")
		        .failureHandler(authFailureHandler)
		        .successHandler(authSuccessHandler)
		        .permitAll()
		        .and()
		    .sessionManagement()
		    	.invalidSessionUrl("/login.html")
		    	.and()
		    .logout()
		        .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
		        .logoutSuccessUrl("/")
		        .permitAll()
		        .and()
		    .authorizeRequests()
		    .anyRequest().fullyAuthenticated().withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {

				@Override
				public <O extends FilterSecurityInterceptor> O postProcess(O object) {
					object.setAccessDecisionManager(customAccessDecisionManager);
					object.setSecurityMetadataSource(customSecurityMetadataSource);
		            return object;
				}

			})
		    .and()
		    .exceptionHandling().accessDeniedHandler(authAccessDeniedHandler);
				
	}

	/**
	 * 认证规则
	 */
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		// System.out.println("==>" + bcrypt.encode("admin"));

		// 内存
//				auth.inMemoryAuthentication().passwordEncoder(myEncoder).withUser("admin").password(myEncoder.encode("admin"))
//						.roles("USER");

		// 数据库
//				auth.jdbcAuthentication().dataSource(dataSource).passwordEncoder(myEncoder).withUser("admin").password(myEncoder.encode("admin"))
//				.roles("USER");

		// 自定义
		// https://spring.io/blog/2017/11/01/spring-security-5-0-0-rc1-released#password-storage-format
		auth.userDetailsService(accountService).passwordEncoder(new PasswordEncoder() {
			
			@Override
			public boolean matches(CharSequence rawPassword, String encodedPassword) {
				return encodedPassword.equals(rawPassword);
			}
			
			@Override
			public String encode(CharSequence rawPassword) {
				return rawPassword.toString();
			}
		});
		
		
	}
	
	@Bean
	public SessionRegistry getSessionRegistry() {
		return new SessionRegistryImpl();
	}

}
