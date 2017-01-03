package com.siv.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.DefaultAuthenticationEventPublisher;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.siv.dao.AbstractAuthenticationProcessingFilterImpl;
import com.siv.dao.AuthenticationSuccessHandlerImpl;

/**
 * 
 * @author Mamta
 *
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	private static final int FOURTEEN_DAYS_IN_SECONDS = 1209600; 
	
	//private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	@Qualifier("userDetailsService")
	UserDetailsService userDetailsService;
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth, ApplicationContext context)
			throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(
				passwordEncoder());
		auth.authenticationEventPublisher(new DefaultAuthenticationEventPublisher(context));
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.csrf().disable();
		
		http
			.headers()
			.defaultsDisabled()
			.frameOptions().sameOrigin();
		
		http.addFilterBefore(authenticationFilter(),
		        UsernamePasswordAuthenticationFilter.class);
		
		http
			.authorizeRequests()
				.antMatchers("/secured/**")
				.authenticated()
				.antMatchers("/api/**")
				.permitAll()
				.and()
				
			.formLogin()
				.loginPage("/login")
				.failureUrl("/?error")
				.successHandler(new AuthenticationSuccessHandlerImpl())
				.usernameParameter("username").passwordParameter("password")
				.permitAll()
				.and()
			.logout()
				.logoutSuccessUrl("/?logout")
				.permitAll()
				// .and().csrf().disable()
				.and()
			.exceptionHandling().accessDeniedPage("/403");
		
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		PasswordEncoder encoder = new BCryptPasswordEncoder();
		return encoder;
	}
	
	
	@Bean
	public SavedRequestAwareAuthenticationSuccessHandler savedRequestAwareAuthenticationSuccessHandler() {

		SavedRequestAwareAuthenticationSuccessHandler auth = new SavedRequestAwareAuthenticationSuccessHandler();
		auth.setTargetUrlParameter("targetUrl");
		return auth;
	}
	
	@Bean
	public AbstractAuthenticationProcessingFilterImpl authenticationFilter() throws Exception {
		AbstractAuthenticationProcessingFilterImpl authFilter = new AbstractAuthenticationProcessingFilterImpl();
		authFilter.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher("/login","POST"));
		authFilter.setAuthenticationManager(authenticationManagerBean());
		authFilter.setAuthenticationSuccessHandler(new AuthenticationSuccessHandlerImpl());
	    return authFilter;
	}
	
}
