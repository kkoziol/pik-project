package com.project.pik.EbayApi.daemon;


import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.async.DeferredResult;

import com.ebay.services.client.ClientConfig;
import com.ebay.services.client.FindingServiceClientFactory;
import com.ebay.services.finding.AspectFilter;
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
import com.project.pik.EbayApi.daos.Offer;
import com.project.pik.EbayApi.daos.Order;
import com.project.pik.EbayApi.service.OfferService;
import com.project.pik.EbayApi.service.OrderService;

public class SearchEbayOffersDaemon extends Thread{
	private static SearchEbayOffersDaemon instance = null;
	private static final String SEARCHING_CURRENCY = "EUR";
	private Map<String, DeferredResult<List<Offer>> > registeredListeners = new HashMap<>();
	private final Logger logger = Logger.getLogger(SearchEbayOffersDaemon.class);

	@Autowired
	private OrderService orderService;
	
	@Autowired
	private OfferService offerService;

	@Autowired
	private ClientConfig eBayClientConfig;
	
	@Override
	public void run() {
		while(true){
			logger.debug("Running search");
			Map<Order, UserPreference> preferences = preparePreferences();
			searchForPreferences(preferences);
			
			List<Offer> of = new ArrayList<>();
			of.add(new Offer());
			registeredListeners.get("test").setResult(of);
		}
	}

	public static SearchEbayOffersDaemon getInstance(){
		if(instance == null){
			instance = new SearchEbayOffersDaemon();
		}
		return instance;
	}
	
	public void registerListener(String userLogin, DeferredResult<List<Offer>> result){
		registeredListeners.put(userLogin, result);
	}
	
	public void unregisterListener(String userLogin){
		// TODO - when user logs out, remove its listener
		registeredListeners.remove(userLogin);
	}
	
	private void searchForPreferences(Map<Order, UserPreference> preferences) {
		preferences.forEach((o, p) -> consumeFoundUrls(o, searchForSinglePreference(p)));
	}

	private void consumeFoundUrls(Order order, List<String> urls) {
		if (urls.isEmpty())
			return;
		for(String url : urls){
			Offer offer = new Offer();
			offer.setCurrent(false);
			offer.setOrder(order);
			offer.setUrl(url);
			offerService.save(offer);
		}
		asyncCallbackToUser(order, offerService.findByUserName(order.getUser().getName()));
	}



	private void asyncCallbackToUser(Order order, List<Offer> offers) {
		String userLogin = order.getUser().getLogin();
		if(registeredListeners.containsKey(userLogin)){
			registeredListeners.get(userLogin).setResult(offers);
		}
	}

	private List<String> searchForSinglePreference(UserPreference preference) {

		List<String> urlsToReturn = new ArrayList<>();
		FindItemsAdvancedRequest fiAdvRequest = new FindItemsAdvancedRequest();
		FindingServicePortType serviceClient = FindingServiceClientFactory.getServiceClient(eBayClientConfig);
		if (preference.getPrizeMin() != null) {
			ItemFilter filter = new ItemFilter();
			filter.setName(ItemFilterType.MIN_PRICE);
			filter.setParamName("Currency");
			filter.setParamValue(SEARCHING_CURRENCY);
			filter.getValue().add(String.valueOf(preference.getPrizeMin()));
			fiAdvRequest.getItemFilter().add(filter);
		}

		if (preference.getPrizeMax() != null) {
			ItemFilter filter = new ItemFilter();
			filter.setName(ItemFilterType.MAX_PRICE);
			filter.setParamName("Currency");
			filter.setParamValue(SEARCHING_CURRENCY);
			filter.getValue().add(String.valueOf(preference.getPrizeMax()));
			fiAdvRequest.getItemFilter().add(filter);
		}

		if (preference.getCondition() != null) {
			// TODO - zmienic condition na conditionsList
			ItemFilter filter = new ItemFilter();
			filter.setName(ItemFilterType.CONDITION);
			filter.setParamValue(mapMnemonicToCode(preference.getCondition()));
			fiAdvRequest.getItemFilter().add(filter);
		}

		if (preference.getCategoryId() != null) {
			fiAdvRequest.getCategoryId().add(preference.getCategoryId());
		}

		Map<String, Set<String>> refinments = preference.getRefinmentsAsSet();
		refinments.forEach((n, v) -> {
			AspectFilter aspectFilter = new AspectFilter();
			aspectFilter.setAspectName(n);
			aspectFilter.getAspectValueName().addAll(v);
			fiAdvRequest.getAspectFilter().add(aspectFilter);
		});
		FindItemsAdvancedResponse response = serviceClient.findItemsAdvanced(fiAdvRequest);
		SearchResult searchResult = response.getSearchResult();
		List<SearchItem> items = searchResult.getItem();
		for (SearchItem item : items)
			urlsToReturn.add(item.getViewItemURL());

		return urlsToReturn;
	}

	private String mapMnemonicToCode(String mnemonic) {
		// https://developer.ebay.com/devzone/finding/callref/types/ItemFilterType.html
		// section: Condition
		if (mnemonic.compareToIgnoreCase("New") == 0) {
			return "1000";
		} else if (mnemonic.compareToIgnoreCase("New other (see details)") == 0) {
			return "1500";
		} else if (mnemonic.compareToIgnoreCase("New with defects") == 0) {
			return "1750";
		} else if (mnemonic.compareToIgnoreCase("Manufacturer refurbished") == 0) {
			return "2000";
		} else if (mnemonic.compareToIgnoreCase("Seller refurbished") == 0) {
			return "2500";
		} else if (mnemonic.compareToIgnoreCase("Used") == 0) {
			return "3000";
		} else if (mnemonic.compareToIgnoreCase("Very Good") == 0) {
			return "4000";
		} else if (mnemonic.compareToIgnoreCase("Good") == 0) {
			return "5000";
		} else if (mnemonic.compareToIgnoreCase("Acceptable") == 0) {
			return "6000";
		} else if (mnemonic.compareToIgnoreCase("For parts or not working") == 0) {
			return "7000";
		} else {
			throw new IllegalStateException("Mnemonic: '" + mnemonic + "'could not be mapped to code");
		}
	}

	private Map<Order, UserPreference> preparePreferences() {
		Map<Order, UserPreference> toReturn = new HashMap<>();
		ObjectMapper jsonMapper = new ObjectMapper();
		List<Order> ordersToSearchFor = orderService.listActiveOrders();
		for (Order order : ordersToSearchFor) {
			String preferenceAsJson = order.getPreferencesAsJson();
			UserPreference preference = null;
			try {
				preference = jsonMapper.readValue(preferenceAsJson, UserPreference.class);
			} catch (JsonParseException | JsonMappingException e) {
				logger.error(e.getMessage());
			} catch (IOException e) {
				logger.error(e.getMessage());
			}
			toReturn.put(order, preference);
		}

		return toReturn;
	}
	
}
