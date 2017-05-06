package com.configuration;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    DataSource dataSource;

    @Autowired
    public void configAuthentication(AuthenticationManagerBuilder auth)
            throws Exception {

        auth.jdbcAuthentication().dataSource(dataSource)
                .passwordEncoder(passwordEncoder())
                .usersByUsernameQuery("SELECT LOGIN, PASSWORD, 1 FROM Users WHERE LOGIN = ?")
                .authoritiesByUsernameQuery("SELECT LOGIN, AUTHORITIES FROM Users WHERE LOGIN = ?  ");
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    @Override
	protected void configure(HttpSecurity http) throws Exception {

	  http.authorizeRequests()
		.antMatchers("/welcome*").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
		.antMatchers("/admin*").access("hasRole('ROLE_ADMIN')")
		.and().formLogin().loginPage("/login").usernameParameter("ssoId").passwordParameter("password").defaultSuccessUrl("/welcome")
		.failureForwardUrl("/loginfailed").and().logout().logoutUrl("/logout");
	}
    
    @Override
    @Bean
    public UserDetailsService userDetailsService() {
    	InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
    	manager.createUser(User.withUsername("user").password("password").roles("USER").build());
    	manager.createUser(User.withUsername("admin").password("password").roles("USER","ADMIN").build());
    	return manager;
    }
}
