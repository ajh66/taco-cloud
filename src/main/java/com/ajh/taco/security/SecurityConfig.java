package com.ajh.taco.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRepository;

@Configuration
@EnableWebSecurity
@Profile("development")
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	private final SecurityConfigProps scp;

	public SecurityConfig(SecurityConfigProps scp) {
		super();
		this.scp = scp;
	}

	@Bean
	public CsrfTokenRepository csrfTokenRepository() {
		return CookieCsrfTokenRepository.withHttpOnlyFalse();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		/*
		 * All HTTP POST should contain CSRF token e.g.
		 * <input type="hidden" name="_csrf" th:value="${_csrf.token}"/>
		 * Or disable CSRF by .and().csrf().disable()
		 * 
		 * */
//		http.csrf().disable();
		http.authorizeRequests()
				.antMatchers("/design", "/design/**", "/orders", "/orders/**").hasRole("USER")
				.antMatchers("/", "/**").permitAll()
			.and()
				.csrf().csrfTokenRepository(csrfTokenRepository())
			.and() // Finish authorization configuration and ready to apply additional HTTP configuration
				.csrf().ignoringAntMatchers("/h2-console/**") // Special case for CSRF
			.and()
				.headers().frameOptions().disable() // For frames in h2-console
			.and()
				.formLogin().defaultSuccessUrl("/design", true) // Enable login form and auto-redirection to this page if necessary
		;
	}

//	@Override
//	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//		auth.inMemoryAuthentication()
//				.withUser("user").password("user").roles("USER")
//				.and()
//				.withUser("admin").password("admin").roles("ADMIN", "USER");
//	}

	@Bean
	@Override
	protected UserDetailsService userDetailsService() {
		@SuppressWarnings("deprecation")
		UserDetails ud = User.withDefaultPasswordEncoder()
				.username(scp.getSampleUserName()).password(scp.getSamplePassword()).roles(scp.getSampleUserRole())
				.build();

//		if (ud instanceof TacoUser) { // Ugly code to set user info
//			TacoUser tu = (TacoUser)ud;
//			tu.setStreet("Zhongshan Road");
//			tu.setCity("Nanjing");
//			tu.setState("Jiangsu");
//			tu.setZip("210000");
//			tu.setPhone("025-12345678");
//		}

		return new InMemoryUserDetailsManager(ud);
	}

}
