package com.project.pik.EbayApi;

import static com.project.pik.EbayApi.consts.ApiConsts.PROPERTIES_FILE_NAME;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;

import com.ebay.sdk.ApiContext;
import com.ebay.sdk.ApiCredential;
import com.ebay.services.client.ClientConfig;
import com.project.pik.EbayApi.daemon.SearchEbayOffersDaemon;
import com.project.pik.EbayApi.service.EbayCategoriesService;
import com.project.pik.EbayApi.service.EbayCategoriesServiceImpl;
import com.project.pik.EbayApi.service.EbayItemsService;
import com.project.pik.EbayApi.service.EbayItemsServiceImpl;

@Configuration
public class ApplicationConfig {

	private static final Logger logger = Logger.getLogger(ApplicationConfig.class);

	@Bean
	public TaskExecutor taskExecutor() {
		return new SimpleAsyncTaskExecutor(); // Or use another one of your
												// liking
	}

	@Bean
	public CommandLineRunner schedulingRunner(TaskExecutor executor) {
		return new CommandLineRunner() {
			public void run(String... args) throws Exception {
				executor.execute(SearchEbayOffersDaemon.getInstance());
			}
		};
	}

	@Bean
	@Lazy
	public ApiContext eBaySoapApi() {
		Properties keys = new Properties();
		try {
			InputStream in = ApplicationConfig.class.getResourceAsStream(PROPERTIES_FILE_NAME);
			keys.load(in);
		} catch (IOException e) {
			logger.error("Could not load ebay properties file");
			logger.error(e.getMessage());
		}
		/** Set ApiAccount and token in ApiCredential */
		ApiCredential credential = new ApiCredential();
		credential.seteBayToken(keys.getProperty("token"));

		ApiContext context = new ApiContext();
		context.setApiCredential(credential);
		context.setApiServerUrl("https://api.ebay.com/wsapi"); // production

		return context;
	}

	@Bean
	@Lazy
	public ClientConfig eBayClientConfig() {
		Properties keys = new Properties();
		try {
			InputStream in = ApplicationConfig.class.getResourceAsStream(PROPERTIES_FILE_NAME);
			keys.load(in);
		} catch (IOException e) {
			logger.error("Could not load ebay properties file");
			logger.error(e.getMessage());
		}
		/** Set ClientConfig for finding API */
		ClientConfig config = new ClientConfig();
		config.setApplicationId(keys.getProperty("appId"));

		return config;
	}

	@Bean
	EbayCategoriesService getEbayCategoriesService() {
		return new EbayCategoriesServiceImpl();
	}

	@Bean
	EbayItemsService getEbayItemsService() {
		return new EbayItemsServiceImpl();
	}

	@Bean
	public SearchEbayOffersDaemon searchEbayOffersDaemon() {
		return SearchEbayOffersDaemon.getInstance();
	}

}
