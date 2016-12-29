package com.siv.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;

/**
 * 
 * @author Mamta
 *
 */
@Configuration
@EnableResourceServer
public class OAuth2ServerConfiguration {
	
private static final String RESOURCE_ID = "rest_api";
	
	@Configuration
	@EnableAuthorizationServer
	protected static class OAuth2Config extends AuthorizationServerConfigurerAdapter {
		
		@Autowired
		private AuthenticationManager authenticationManager;
					
		@Override
		public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
			endpoints
				.tokenStore(tokenStore())
	            .authenticationManager(authenticationManager);
			
		}
		
		@Override
		public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
			oauthServer
	              .tokenKeyAccess("permitAll()")
	              .checkTokenAccess("isAuthenticated()");
			
		}
		
		@Override
		public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
			clients.inMemory().withClient("test_client")
			.authorities("SuperAdmin")
			.accessTokenValiditySeconds(60)
			.authorizedGrantTypes("password")
			.secret("12345")
			.autoApprove(true)
			.redirectUris("http://google.com/")
			.resourceIds(RESOURCE_ID).scopes("Read");
		
		}
		
		@Bean
		public TokenStore tokenStore() {
			return new InMemoryTokenStore();
			
		}
		
	}
	
	@Configuration
	@EnableResourceServer
	protected static class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {
		
		@Override
		public void configure(ResourceServerSecurityConfigurer resources) {
			resources.resourceId(RESOURCE_ID);
			
		}
		
		@Override
		public void configure(HttpSecurity http) throws Exception {
			http
            .requestMatchers().antMatchers("/api/**")
            .and()
            .authorizeRequests()
            .anyRequest().access("#oauth2.hasScope('read')");
			
		}
				
	}

}
