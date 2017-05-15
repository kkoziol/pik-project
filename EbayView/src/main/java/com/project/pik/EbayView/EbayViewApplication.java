package com.project.pik.EbayView;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.project.pik.EbayView.services.UserService;
import com.project.pik.EbayView.services.UserServiceImpl;

@SpringBootApplication
public class EbayViewApplication {

	public static void main(String[] args) throws Exception {
		SpringApplication.run(EbayViewApplication.class, args);
	}

	@Bean
	public EmbeddedServletContainerCustomizer containerCustomizer() {
		return container -> container.setPort(8080);
	}
	
	
	@Bean UserService userService() {
		return new UserServiceImpl();
	}

}
