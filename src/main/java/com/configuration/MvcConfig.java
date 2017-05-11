package com.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.daos.UserDaoImpl;
import com.services.EbayServiceImpl;
import com.services.UserServiceImpl;

@EnableWebMvc
@Configuration
@ComponentScan(basePackages = { "com" })
public class MvcConfig extends WebMvcConfigurerAdapter {
	@Override
	public void addResourceHandlers(final ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/static/**").addResourceLocations("/WEB-INF/static/html/");
		registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
		registry.addResourceHandler("/css/**").addResourceLocations("/WEB-INF/assets/css/");
		registry.addResourceHandler("/js/**").addResourceLocations("/WEB-INF/assets/js/");
	}
// 
//	@Override
//	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
//		configurer.enable();
//	}
// 
//	@Bean
//	public InternalResourceViewResolver jspViewResolver() {
//		InternalResourceViewResolver bean = new InternalResourceViewResolver();
//		bean.setPrefix("/WEB-INF/public/");
//		bean.setSuffix(".html");
//		return bean;
//	}
 
//	@Bean(name = "multipartResolver")
//	public CommonsMultipartResolver getMultipartResolver() {
//		return new CommonsMultipartResolver();
//	}
 
//	@Bean(name = "messageSource")
//	public ReloadableResourceBundleMessageSource getMessageSource() {
//		ReloadableResourceBundleMessageSource resource = new ReloadableResourceBundleMessageSource();
//		resource.setBasename("classpath:messages");
//		resource.setDefaultEncoding("UTF-8");
//		return resource;
//	}
	
	@Bean
	public UserDaoImpl userDao() {
		return new UserDaoImpl();
	}
	
	
	@Bean
	public UserServiceImpl userService() {
		return new UserServiceImpl();
	}
	
	@Bean
	public EbayServiceImpl ebayService() {
		return new EbayServiceImpl();
	}
}
