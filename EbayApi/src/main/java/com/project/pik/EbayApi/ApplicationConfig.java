package com.project.pik.EbayApi;

import static com.project.pik.EbayApi.consts.ApiConsts.PROPERTIES_FILE_NAME;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.ebay.sdk.ApiContext;
import com.ebay.sdk.ApiCredential;
import com.ebay.sdk.ApiLogging;
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
	public ApiContext eBaySoapApi() throws IOException {
		Properties keys = new Properties();
		try {
			InputStream in = ApplicationConfig.class.getResourceAsStream(PROPERTIES_FILE_NAME);
			keys.load(in);
		} catch (IOException e) {
			logger.error("Could not load ebay properties file");
			logger.error(e.getMessage());
			throw e;
		}
		/** Set ApiAccount and token in ApiCredential */
		ApiCredential credential = new ApiCredential();
		credential.seteBayToken(keys.getProperty("token"));

		ApiContext context = new ApiContext();
		context.setApiCredential(credential);
		context.setApiServerUrl("https://api.ebay.com/wsapi"); // production
		ApiLogging logger = new ApiLogging();
		logger.setLogSOAPMessages(false);
		logger.setLogHTTPHeaders(false);
		logger.setLogExceptions(false);
		context.setApiLogging(logger);

		return context;
	}

	@Bean
	public ClientConfig eBayClientConfig() throws IOException {
		Properties keys = new Properties();
		try {
			InputStream in = ApplicationConfig.class.getResourceAsStream(PROPERTIES_FILE_NAME);
			keys.load(in);
		} catch (IOException e) {
			logger.error("Could not load ebay properties file");
			logger.error(e.getMessage());
			throw e;
		}
		/** Set ClientConfig for finding API */
		ClientConfig config = new ClientConfig();
		config.setApplicationId(keys.getProperty("appId"));
		config.setHttpHeaderLoggingEnabled(false);
		config.setSoapMessageLoggingEnabled(false);

		return config;
	}

	
	@Bean(initMethod = "start")
	public SearchEbayOffersDaemon searchEbayOffersDaemon() {
		return SearchEbayOffersDaemon.getInstance();
	}

	@Bean
	public EbayCategoriesService getEbayCategoriesService() {
		return new EbayCategoriesServiceImpl();
	}

	@Bean
	public EbayItemsService getEbayItemsService() {
		return new EbayItemsServiceImpl();
	}
}
