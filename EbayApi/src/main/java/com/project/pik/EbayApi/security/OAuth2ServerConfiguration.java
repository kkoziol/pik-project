package com.project.pik.EbayApi.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
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
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;

@Configuration
public class OAuth2ServerConfiguration {

	private static final String RESOURCE_ID = "my_rest_ebay_api";

	@Autowired
	TokenStore tokenStore;

	@Configuration
	@EnableResourceServer
	protected class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

		@Override
		public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
			resources.resourceId(RESOURCE_ID);
		}

		@Override
		public void configure(HttpSecurity http) throws Exception {
//			http.requestMatchers().antMatchers("/api/**").and().authorizeRequests().antMatchers("/api/**")
//					.access("hasRole('USER')").and().exceptionHandling()
//					.accessDeniedHandler(new OAuth2AccessDeniedHandler());
			
			http.authorizeRequests().antMatchers("/categories/**")
			.access("hasRole('USER')").antMatchers("/items/**")
			.access("hasRole('USER')").antMatchers("/api/**")
			.access("hasRole('USER')");
		}

	}

	@Configuration
	@EnableAuthorizationServer
	protected class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {

//		@Autowired
//		@Qualifier("userApprovalHandler")
//		TokenStoreUserApprovalHandler userApprovalHandler;

		@Autowired
		@Qualifier("authenticationManagerBean")
		private AuthenticationManager authenticationManager;

		@Override
		public void configure(final AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
			oauthServer.tokenKeyAccess("permitAll()").checkTokenAccess("isAuthenticated()");
		}

		@Override
		public void configure(ClientDetailsServiceConfigurer clients) throws Exception {

			clients.inMemory() // we have one client so for developing it could
								// be enaugh
					.withClient("pik-webapp-client")
					.authorizedGrantTypes("password", "authorization_code", "refresh_token", "implicit")
					.authorities("ROLE_CLIENT", "ROLE_TRUSTED_CLIENT").scopes("read", "write", "trust")
					.resourceIds(RESOURCE_ID).secret("secret").accessTokenValiditySeconds(60 * 60) // 1h
					.refreshTokenValiditySeconds(60 * 90); // 1.5h
		}

		@Override
		public void configure(final AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
			endpoints.tokenStore(tokenStore)// .userApprovalHandler(userApprovalHandler)
					.authenticationManager(authenticationManager);
		}

		 @Bean
		 @Primary
		 public DefaultTokenServices tokenServices() {
		 DefaultTokenServices tokenServices = new DefaultTokenServices();
		 tokenServices.setTokenStore(tokenStore);
		 tokenServices.setSupportRefreshToken(true);
		 return tokenServices;
		 }

	}
}
