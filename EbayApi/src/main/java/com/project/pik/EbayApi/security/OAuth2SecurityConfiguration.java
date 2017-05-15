package com.project.pik.EbayApi.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.approval.ApprovalStore;
import org.springframework.security.oauth2.provider.approval.TokenApprovalStore;
import org.springframework.security.oauth2.provider.approval.TokenStoreUserApprovalHandler;
import org.springframework.security.oauth2.provider.request.DefaultOAuth2RequestFactory;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class OAuth2SecurityConfiguration extends WebSecurityConfigurerAdapter{
	
		@Autowired
	    private ClientDetailsService clientDetailsService;
	    
		
		
		@Override
	    public void configure(AuthenticationManagerBuilder auth) throws Exception {
	        auth.inMemoryAuthentication()
	        .withUser("admin").password("admin").roles("ADMIN").and()
	        .withUser("test").password("test").roles("USER").and()
	        .withUser("pik-webapp-client").password("secret").roles("USER");
	    }
	    
	    
	 
	    @Override
	    protected void configure(HttpSecurity http) throws Exception {
	        http
	        .csrf().disable()
	        .anonymous().disable()
	        .authorizeRequests()
	        .antMatchers("/oauth/token").permitAll()
	        .anyRequest().access("hasRole('USER')")
	        .and().httpBasic()
	        ;
	    
	    }
	 
	    @Override
	    @Bean
	    public AuthenticationManager authenticationManagerBean() throws Exception {
	        return super.authenticationManagerBean();
	    }
	 
	 
	    @Bean
	    public TokenStore tokenStore() {
	        return new InMemoryTokenStore();
	    }
	 
	    @Bean
	    @Autowired
	    public TokenStoreUserApprovalHandler userApprovalHandler(TokenStore tokenStore){
	        TokenStoreUserApprovalHandler handler = new TokenStoreUserApprovalHandler();
	        handler.setTokenStore(tokenStore);
	        handler.setRequestFactory(new DefaultOAuth2RequestFactory(clientDetailsService));
	        handler.setClientDetailsService(clientDetailsService);
	        return handler;
	    }
	     
	    @Bean
	    @Autowired
	    public ApprovalStore approvalStore(TokenStore tokenStore) throws Exception {
	        TokenApprovalStore store = new TokenApprovalStore();
	        store.setTokenStore(tokenStore);
	        return store;
	    }
}
