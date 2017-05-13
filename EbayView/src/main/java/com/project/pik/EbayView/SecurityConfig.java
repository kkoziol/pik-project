package com.project.pik.EbayView;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;

import com.project.pik.EbayView.filters.CsrfHeaderFilter;



@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
//
//    @Autowired
//    DataSource dataSource;

	@Override
    protected void configure(AuthenticationManagerBuilder builder) throws Exception {
        builder.inMemoryAuthentication().withUser("test").password("test").roles("USER").and().withUser("admin")
                .password("admin").roles("ADMIN");
    }
	
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.httpBasic().and().authorizeRequests()
				.antMatchers("/js/**","/css/**","/login-post","/user","/index.html", "/home.html", "/","/login.html")
				.permitAll().anyRequest().authenticated()
				.and().formLogin().loginPage("/index.html#/login").permitAll()
				//.defaultSuccessUrl("/home.html").permitAll()
				.and()
				.addFilterAfter(new CsrfHeaderFilter(), CsrfFilter.class)
				.csrf().csrfTokenRepository(csrfTokenRepository())
				.and().logout().logoutSuccessUrl("/login").deleteCookies("XSRF-TOKEN");

	}
	
	/*
	 * tell Spring Security to expect Angular CSRF token format - XSRF-TOKEN
	 */
	private CsrfTokenRepository csrfTokenRepository() {
		  HttpSessionCsrfTokenRepository repository = new HttpSessionCsrfTokenRepository();
		  repository.setHeaderName("X-XSRF-TOKEN");
		  return repository;
		}
	
	@Override
    public void configure(WebSecurity web) throws Exception {
      web
        .ignoring()
           .antMatchers("/WEB-INF/**"); // #3
    }
	
//    @Autowired
//    public void configAuthentication(AuthenticationManagerBuilder auth)
//            throws Exception {
//
//        auth.jdbcAuthentication().dataSource(dataSource)
//                .passwordEncoder(passwordEncoder())
//                .usersByUsernameQuery("SELECT LOGIN, PASSWORD, 1 FROM Users WHERE LOGIN = ?")
//                .authoritiesByUsernameQuery("SELECT LOGIN, AUTHORITIES FROM Users WHERE LOGIN = ?  ");
//    }

//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//    
//    @Override
//	protected void configure(HttpSecurity http) throws Exception {
//
//	  http.authorizeRequests()
//		.antMatchers("/welcome*").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
//		.antMatchers("/admin*").access("hasRole('ROLE_ADMIN')")
//		.and().formLogin().loginPage("/login").usernameParameter("ssoId").passwordParameter("password").defaultSuccessUrl("/welcome")
//		.failureForwardUrl("/loginfailed").and().logout().logoutUrl("/logout");
//	}
//    
//    @Override
//    @Bean
//    public UserDetailsService userDetailsService() {
//    	InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
//    	manager.createUser(User.withUsername("user").password("password").roles("USER").build());
//    	manager.createUser(User.withUsername("admin").password("password").roles("USER","ADMIN").build());
//    	return manager;
//    }
}
