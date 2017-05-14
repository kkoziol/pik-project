package com.project.pik.EbayApi.daemon;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;

import com.ebay.sdk.ApiContext;
import com.ebay.services.client.ClientConfig;
import com.ebay.services.client.FindingServiceClientFactory;
import com.ebay.services.finding.FindItemsAdvancedRequest;
import com.ebay.services.finding.FindItemsAdvancedResponse;
import com.ebay.services.finding.FindingServicePortType;
import com.ebay.services.finding.ItemFilter;
import com.ebay.services.finding.ItemFilterType;
import com.ebay.services.finding.SearchItem;
import com.ebay.services.finding.SearchResult;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.pik.EbayApi.daos.Order;
import com.project.pik.EbayApi.service.OrderService;

public class SearchEbayOffersDaemon {
	private static final int TIME_INTERVAL_SEC = 1;
	private static final int INITIAL_DELAY_SEC = 5; // lets wait for the spring container to start
	private static SearchEbayOffersDaemon instance = null;
	
	public static void runInstance(){
		if(instance == null){
			instance = new SearchEbayOffersDaemon();
		}
	}
	
	private SearchEbayOffersDaemon(){
		ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
		executor.scheduleAtFixedRate(new SearchThread(), INITIAL_DELAY_SEC, TIME_INTERVAL_SEC, TimeUnit.SECONDS);
	}
}
