package com.project.pik.EbayApi.service;

import static com.project.pik.EbayApi.consts.ApiConsts.SEARCHING_CURRENCY;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.ebay.services.client.ClientConfig;
import com.ebay.services.client.FindingServiceClientFactory;
import com.ebay.services.finding.FindItemsAdvancedRequest;
import com.ebay.services.finding.FindItemsAdvancedResponse;
import com.ebay.services.finding.FindingServicePortType;
import com.ebay.services.finding.ItemFilter;
import com.ebay.services.finding.ItemFilterType;
import com.ebay.services.finding.PaginationInput;
import com.ebay.services.finding.PaginationOutput;
import com.ebay.services.finding.SearchItem;
import com.ebay.services.finding.SortOrderType;

public class EbayItemsServiceImpl implements EbayItemsService {
	@Autowired
	private ClientConfig eBayClientConfig;

	@Override
	public List<SearchItem> getItemsByKeyword(String keyword,int pageNumber) {
		return this.getItemsByKeywordCategoryAndPrice(keyword, null, -1, -1,pageNumber);
	}

	@Override
	public List<SearchItem> getItemsByKeywordCategory(String keyword, String categoryId,int pageNumber) {
		return this.getItemsByKeywordCategoryAndPrice(keyword, categoryId, -1, -1, pageNumber);
	}

	@Override
	public List<SearchItem> getItemsByKeywordCategoryAndPrice(String keyword, String categoryId, int minPrice,
			int maxPrice,int pageNumber) {
		FindingServicePortType serviceClient = FindingServiceClientFactory.getServiceClient(eBayClientConfig);
		FindItemsAdvancedRequest fiAdvRequest = new FindItemsAdvancedRequest();
		// set request parameters
		fiAdvRequest.setKeywords(keyword);
		fiAdvRequest.setSortOrder(SortOrderType.BEST_MATCH);
		fiAdvRequest.setDescriptionSearch(false);
		/** ADD CATEGORY */
		if (categoryId != null)
			fiAdvRequest.getCategoryId().add(categoryId);

		/** ADD FILTERS */
		if (minPrice > 0) {
			ItemFilter filterMin = new ItemFilter();
			filterMin.setName(ItemFilterType.MIN_PRICE);
			filterMin.setParamName("Currency");
			filterMin.setParamValue(SEARCHING_CURRENCY);
			filterMin.getValue().add(String.valueOf(minPrice));
			fiAdvRequest.getItemFilter().add(filterMin);
		}

		if (maxPrice > 0) {
			ItemFilter filterMax = new ItemFilter();
			filterMax.setName(ItemFilterType.MAX_PRICE);
			filterMax.setParamName("Currency");
			filterMax.setParamValue(SEARCHING_CURRENCY);
			filterMax.getValue().add(String.valueOf(maxPrice));
			fiAdvRequest.getItemFilter().add(filterMax);
		}

		PaginationInput pagesIn = new PaginationInput();
		pagesIn.setPageNumber(pageNumber);
		fiAdvRequest.setPaginationInput(pagesIn);
		
		
		
		FindItemsAdvancedResponse fiAdvResponse = serviceClient.findItemsAdvanced(fiAdvRequest);
		PaginationOutput pages = new PaginationOutput();
		pages.setPageNumber(pageNumber);
		fiAdvResponse.setPaginationOutput(pages);
		List<SearchItem> items = new ArrayList<>();
		
		if (fiAdvResponse.getSearchResult() != null
				&& !fiAdvResponse.getSearchResult().getItem().isEmpty())
			items.addAll(fiAdvResponse.getSearchResult().getItem());


		return items;
	}

	@Override
	public SearchItem getBestMatchItem(String keyword) {
		FindingServicePortType serviceClient = FindingServiceClientFactory.getServiceClient(eBayClientConfig);

		FindItemsAdvancedRequest fiAdvRequest = new FindItemsAdvancedRequest();
		// set request parameters
		fiAdvRequest.setKeywords(keyword);
		fiAdvRequest.setSortOrder(SortOrderType.BEST_MATCH);
		PaginationInput pages = new PaginationInput();
		pages.setPageNumber(1);
		fiAdvRequest.setPaginationInput(pages);
		/** Call service */
		FindItemsAdvancedResponse fiAdvResponse = serviceClient.findItemsAdvanced(fiAdvRequest);
		/** Handle response */

		if (fiAdvResponse != null && fiAdvResponse.getSearchResult() != null
				&& !fiAdvResponse.getSearchResult().getItem().isEmpty())
			return fiAdvResponse.getSearchResult().getItem().get(0);

		return new SearchItem();
	}

	@Override
	public SearchItem getCheapestItemByKeywordAndCategory(String keyword, String categoryId) {
		FindingServicePortType serviceClient = FindingServiceClientFactory.getServiceClient(eBayClientConfig);

		FindItemsAdvancedRequest fiAdvRequest = new FindItemsAdvancedRequest();
		// set request parameters
		fiAdvRequest.setKeywords(keyword);
		fiAdvRequest.setSortOrder(SortOrderType.PRICE_PLUS_SHIPPING_LOWEST);
		PaginationInput pages = new PaginationInput();
		pages.setPageNumber(1);
		fiAdvRequest.setPaginationInput(pages);
		/** Call service */
		FindItemsAdvancedResponse fiAdvResponse = serviceClient.findItemsAdvanced(fiAdvRequest);
		/** Handle response */

		if (fiAdvResponse != null && fiAdvResponse.getSearchResult() != null
				&& !fiAdvResponse.getSearchResult().getItem().isEmpty())
			return fiAdvResponse.getSearchResult().getItem().get(0);

		return new SearchItem();
	}

}
