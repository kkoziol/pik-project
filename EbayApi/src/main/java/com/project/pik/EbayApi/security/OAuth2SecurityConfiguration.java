package com.project.pik.EbayApi.security;


import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;


@Configuration
//@EnableWebSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true)
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
public class OAuth2SecurityConfiguration extends WebSecurityConfigurerAdapter{
	

	    
		  @Autowired
		  DataSource dataSource;
		
		@Autowired
	    public void globalUserDetails(final AuthenticationManagerBuilder auth) throws Exception {
	        auth.jdbcAuthentication().dataSource(dataSource)
	               // .passwordEncoder(passwordEncoder())
	                .usersByUsernameQuery("SELECT LOGIN, PASSWORD, 1 FROM Users WHERE LOGIN = ?")
	                .authoritiesByUsernameQuery("SELECT LOGIN, AUTHORITIES FROM Users WHERE LOGIN = ?  ");
	    }
	    
	    
	 
	    @Override
	    protected void configure(final HttpSecurity http) throws Exception {
	        http
	        .csrf().disable()
	        .anonymous().disable()
	        .authorizeRequests()
	        .antMatchers("/oauth/token").permitAll()
	        .anyRequest().authenticated();
	    
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
	
}
