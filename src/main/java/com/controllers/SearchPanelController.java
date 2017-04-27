package com.controllers;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ebay.services.client.ClientConfig;
import com.ebay.services.client.FindingServiceClientFactory;
import com.ebay.services.finding.FindItemsByKeywordsRequest;
import com.ebay.services.finding.FindItemsByKeywordsResponse;
import com.ebay.services.finding.FindingServicePortType;
import com.ebay.services.finding.PaginationInput;
import com.ebay.services.finding.SearchItem;
import com.model.UsersQuery;


@Controller
@RequestMapping("/search")
public class SearchPanelController {
	
	private static final Logger logger = Logger.getLogger(SearchPanelController.class);
	
	private static final String AUTHENTIFICATION_TOKEN = "MariuszZ-PIKalleg-PRD-508fa563f-af4092fd";

	@RequestMapping(method = RequestMethod.GET)
	public String initSearch(Model model) {
		model.addAttribute("usersQuery", new UsersQuery());
		return "initSearch";
	}

	@RequestMapping(method = RequestMethod.POST)
	public String showSeachResults(Model model, @ModelAttribute("usersQuery") UsersQuery usersInput) {

		logger.debug("------------ WEW showSeachResutls, usersInput" + usersInput);
		StringBuilder builder = new StringBuilder();
		try {
			// initialize service end-point configuration
			ClientConfig config = new ClientConfig();
			config.setApplicationId(AUTHENTIFICATION_TOKEN);
			// create a service client
			FindingServicePortType serviceClient = FindingServiceClientFactory.getServiceClient(config);
			// create request object
			FindItemsByKeywordsRequest request = new FindItemsByKeywordsRequest();
			// set request parameters
			request.setKeywords(usersInput.getQuery());
			PaginationInput pi = new PaginationInput();
			pi.setEntriesPerPage(2);
			request.setPaginationInput(pi);
			// call service
			FindItemsByKeywordsResponse result = serviceClient.findItemsByKeywords(request);
			// output result
			logger.debug("Ack = " + result.getAck());
			logger.debug("Find " + result.getSearchResult().getCount() + " items.");
			List<SearchItem> items = result.getSearchResult().getItem();
			for (SearchItem item : items) {
				item.getGalleryURL(); // url obrazka
				item.getViewItemURL();
				item.getLocation();
				builder.append(item.getTitle() + "\n" + item.getViewItemURL() + "\n\n\n");
				logger.debug(item.getTitle() + builder.toString());
			}
		} catch(NoClassDefFoundError e){
			logger.error("Sorry, something wrong!", e);
		} catch (Exception ex) {
			logger.error("Sorry, something wrong!", ex);
		} 

		model.addAttribute("results", builder.toString());
		
		return "showSearchResults";
	}

}
