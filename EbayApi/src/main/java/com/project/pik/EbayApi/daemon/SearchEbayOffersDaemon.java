package com.project.pik.EbayApi.daemon;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.PreDestroy;
import javax.persistence.EntityManager;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.async.DeferredResult;

import com.ebay.services.client.ClientConfig;
import com.ebay.services.client.FindingServiceClientFactory;
import com.ebay.services.finding.AspectFilter;
import com.ebay.services.finding.FindItemsAdvancedRequest;
import com.ebay.services.finding.FindItemsAdvancedResponse;
import com.ebay.services.finding.FindingServicePortType;
import com.ebay.services.finding.ItemFilter;
import com.ebay.services.finding.ItemFilterType;
import com.ebay.services.finding.PaginationInput;
import com.ebay.services.finding.SearchItem;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.pik.EbayApi.mail.MailSender;
import com.project.pik.EbayApi.model.Email;
import com.project.pik.EbayApi.model.FoundResult;
import com.project.pik.EbayApi.model.Order;
import com.project.pik.EbayApi.model.User;
import com.project.pik.EbayApi.model.UserPreference;
import com.project.pik.EbayApi.repositories.FoundResultRepository;
import com.project.pik.EbayApi.repositories.OrderRepository;

@Component
public class SearchEbayOffersDaemon extends Thread {
	private static SearchEbayOffersDaemon instance = null;
	private static final String SEARCHING_CURRENCY = "EUR";
	private Map<String, DeferredResult<List<FoundResult>>> registeredListeners = new HashMap<>();
	private final Logger logger = Logger.getLogger(SearchEbayOffersDaemon.class);
	private volatile boolean threadWorking;
	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private FoundResultRepository foundResultRepository;

	@Autowired
	private EntityManager entityManager;

	@Autowired
	private ClientConfig eBayClientConfig;

    @PreDestroy
    @Override
    public void destroy() {
    	threadWorking = false;
        this.interrupt();
    }

	@Override
	public void run() {
		threadWorking = true;
		while (threadWorking) {
			logger.debug("Running search");
			Map<Order, UserPreference> preferences = preparePreferences();
			searchForPreferences(preferences);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				logger.error("Thread interruped");
				logger.error(e.getMessage());
			}
		}
	}

	public static SearchEbayOffersDaemon getInstance() {
		if (instance == null) {
			instance = new SearchEbayOffersDaemon();
		}
		return instance;
	}

	public synchronized void registerListener(String userLogin, DeferredResult<List<FoundResult>> result) {
		registeredListeners.put(userLogin, result);
	}

	public synchronized void unregisterListener(String userLogin) {
		// TODO - when user logs out, remove its listener
		registeredListeners.remove(userLogin);
	}

	private void searchForPreferences(Map<Order, UserPreference> preferences) {
		preferences.forEach((o, p) -> consumeFoundUrls(o, searchForSinglePreference(p)));
	}

	private void consumeFoundUrls(Order order, List<String> urls) {
		if (urls.isEmpty())
			return;
		String urlsInClause = "(" + urls.stream().map(u -> "'" + u + "'").collect(Collectors.joining(",")) + ")";
		@SuppressWarnings("unchecked")
		List<String> urlsInDb = entityManager
				.createNativeQuery("SELECT url FROM FOUND_RESULTS WHERE url in " + urlsInClause, String.class)
				.getResultList();
		urls.removeAll(urlsInDb);
		if (urls.isEmpty()) {
			return;
		}
		User user = order.getUser();
		String login = user.getLogin();
		asyncCallbackToUser(login, foundResultRepository.findByOrderUserLogin(login));
		MailSender sender = new MailSender();
		for (Email email : order.getUser().getEmails()) {
			sender.sendFoundOffer(email, order, urls);
		}
	}

	private void asyncCallbackToUser(String login, List<FoundResult> results) {
		if (registeredListeners.containsKey(login)) {
			registeredListeners.get(login).setResult(results);
		}
	}

	private List<String> searchForSinglePreference(UserPreference preference) {

		List<String> urlsToReturn = new ArrayList<>();
		FindItemsAdvancedRequest fiAdvRequest = new FindItemsAdvancedRequest();
		fiAdvRequest.setDescriptionSearch(false);
		if (preference.getKeyword() != null) {
			fiAdvRequest.setKeywords(preference.getKeyword());
		}

		if (preference.getCategoryId() != null) {
			fiAdvRequest.getCategoryId().add(preference.getCategoryId());
		}

		if (preference.getPriceMin() != null) {
			ItemFilter filter = new ItemFilter();
			filter.setName(ItemFilterType.MIN_PRICE);
			filter.setParamName("Currency");
			filter.setParamValue(SEARCHING_CURRENCY);
			filter.getValue().add(String.valueOf(preference.getPriceMin()));
			fiAdvRequest.getItemFilter().add(filter);
		}
		if (preference.getPriceMax() != null) {
			ItemFilter filter = new ItemFilter();
			filter.setName(ItemFilterType.MAX_PRICE);
			filter.setParamName("Currency");
			filter.setParamValue(SEARCHING_CURRENCY);
			filter.getValue().add(String.valueOf(preference.getPriceMax()));
			fiAdvRequest.getItemFilter().add(filter);
		}

		if (preference.getConditions() != null && !preference.getConditions().isEmpty()) {
			ItemFilter filter = new ItemFilter();
			filter.setName(ItemFilterType.CONDITION);
			filter.getValue().addAll(preference.getConditions().stream().map(UserPreference::mapMnemonicToCode)
					.collect(Collectors.toList()));
			fiAdvRequest.getItemFilter().add(filter);
		}

		Map<String, Set<String>> refinments = preference.getCategorySpecifics();
		if (refinments != null) {
			refinments.forEach((n, v) -> {
				AspectFilter aspectFilter = new AspectFilter();
				aspectFilter.setAspectName(n);
				aspectFilter.getAspectValueName().addAll(v);
				fiAdvRequest.getAspectFilter().add(aspectFilter);
			});
		}

		PaginationInput pages = new PaginationInput();
		pages.setPageNumber(1);
		pages.setEntriesPerPage(20);
		fiAdvRequest.setPaginationInput(pages);

		FindingServicePortType serviceClient = FindingServiceClientFactory.getServiceClient(eBayClientConfig);
		FindItemsAdvancedResponse response = serviceClient.findItemsAdvanced(fiAdvRequest);

		if (response.getSearchResult() == null || response.getSearchResult().getItem().isEmpty())
			return new ArrayList<>();

		List<String> foundedUrls = response.getSearchResult().getItem().stream().map(SearchItem::getViewItemURL)
				.collect(Collectors.toList());
		urlsToReturn.addAll(foundedUrls);

		return urlsToReturn;
	}

	private Map<Order, UserPreference> preparePreferences() {
		Map<Order, UserPreference> toReturn = new HashMap<>();
		ObjectMapper jsonMapper = new ObjectMapper();
		List<Order> ordersToSearchFor = orderRepository.findByIsHistoryLog(false);
		ordersToSearchFor.forEach(o -> {
			try {
				toReturn.put(o, jsonMapper.readValue(o.getPreferencesAsJson(), UserPreference.class));
			} catch (JsonParseException | JsonMappingException e) {
				logger.error(e.getMessage());
			} catch (IOException e) {
				logger.error(e.getMessage());
			}
		});

		return toReturn;
	}

}
