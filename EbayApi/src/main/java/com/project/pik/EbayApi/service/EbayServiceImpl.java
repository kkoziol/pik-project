package com.project.pik.EbayApi.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.ebay.sdk.ApiContext;
import com.ebay.sdk.ApiCredential;
import com.ebay.sdk.SdkException;
import com.ebay.sdk.call.GetCategoriesCall;
import com.ebay.sdk.call.GetCategorySpecificsCall;
import com.ebay.sdk.call.GeteBayOfficialTimeCall;
import com.ebay.services.client.ClientConfig;
import com.ebay.services.client.FindingServiceClientFactory;
import com.ebay.services.finding.FindItemsAdvancedRequest;
import com.ebay.services.finding.FindItemsAdvancedResponse;
import com.ebay.services.finding.FindingServicePortType;
import com.ebay.services.finding.ItemFilter;
import com.ebay.services.finding.ItemFilterType;
import com.ebay.services.finding.PaginationInput;
import com.ebay.services.finding.SearchItem;
import com.ebay.services.finding.SortOrderType;
import com.ebay.soap.eBLBaseComponents.CategoryType;
import com.ebay.soap.eBLBaseComponents.DetailLevelCodeType;
import com.ebay.soap.eBLBaseComponents.NameRecommendationType;
import com.ebay.soap.eBLBaseComponents.RecommendationsType;
import com.ebay.soap.eBLBaseComponents.SiteCodeType;
import com.ebay.soap.eBLBaseComponents.ValueRecommendationType;

public class EbayServiceImpl implements EbayService {

	@Autowired
	private ApiContext eBaySoapApi;
	
	@Autowired
	private ClientConfig eBayClientConfig;
	
	private static final Logger logger = Logger.getLogger(EbayServiceImpl.class);
	/** CONSTS */
	private static final SiteCodeType SITE_CODING = SiteCodeType.US;
	private static final String SEARCHING_CURRENCY = "EUR";


	public Map<String, List<String>> getCategorySpecificsByCategoryId(String categoryId){
		Map<String, List<String>> toReturn = new HashMap<>();
		GetCategorySpecificsCall call = new GetCategorySpecificsCall();
		call.setApiContext(eBaySoapApi);
		call.setCategoryID(new String[]{categoryId});
		RecommendationsType[] categorySpecifics = null;
		try {
			categorySpecifics = call.getCategorySpecifics();
		} catch (Exception e) {
			e.printStackTrace();
		}		
		NameRecommendationType[] nameRecommendation = categorySpecifics[0].getNameRecommendation();
		for(NameRecommendationType nrt : nameRecommendation){
			List<String> val = new ArrayList<>();
			for(ValueRecommendationType vrt : nrt.getValueRecommendation()){
				val.add(vrt.getValue());
			}
			toReturn.put(nrt.getName(), val);
		}
		return toReturn;		
	}
	
	public CategoryType getCategoryById(String categoryId) {
		GetCategoriesCall categoriesCall = new GetCategoriesCall(eBaySoapApi);
		categoriesCall.setCategorySiteID(SITE_CODING);
		categoriesCall.addDetailLevel(DetailLevelCodeType.RETURN_ALL);

		CategoryType[] categories = null;
		try {
			categories = categoriesCall.getCategories();
		} catch (SdkException e) {
			logger.error(e.getMessage());
		} catch (Exception e) {
			logger.error(e.getMessage());
		}

		Optional<CategoryType> category = Arrays.asList(categories).stream()
				.filter(c -> c.getCategoryID().equals(categoryId)).findAny();
		if (category.isPresent())
			return category.get();

		return null;
	}

	@Override
	public Calendar getEbayTime() {
		GeteBayOfficialTimeCall apiCall = new GeteBayOfficialTimeCall(eBaySoapApi);
		Calendar cal = null;

		try {
			cal = apiCall.geteBayOfficialTime();
		} catch (SdkException e) {
			logger.error(e.getMessage());
		} catch (Exception e) {
			logger.error(e.getMessage());
		}

		if (cal == null) {
			logger.error("Couldnot get ebay time");
			return null;
		}

		return cal;
	}

	@Override
	public List<SearchItem> getItemsByKeywordCategoryAndPrice(String keyword, String categoryId, int minPrice,
			int maxPrice) {
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

		FindItemsAdvancedResponse fiAdvResponse = serviceClient.findItemsAdvanced(fiAdvRequest);
		int returnedPageNumber = fiAdvResponse.getPaginationOutput().getTotalPages();
		List<SearchItem> items = new ArrayList<>();
		if (fiAdvResponse != null && fiAdvResponse.getSearchResult() != null
				&& !fiAdvResponse.getSearchResult().getItem().isEmpty())
			items.addAll(fiAdvResponse.getSearchResult().getItem());

		int pageNumber = 2;
		if (returnedPageNumber > 1) {
			while (pageNumber < returnedPageNumber) {
				PaginationInput pages = new PaginationInput();
				pages.setPageNumber(pageNumber);
				fiAdvRequest.setPaginationInput(pages);
				fiAdvResponse = serviceClient.findItemsAdvanced(fiAdvRequest);
				if (fiAdvResponse != null && fiAdvResponse.getSearchResult() != null
						&& !fiAdvResponse.getSearchResult().getItem().isEmpty())
					items.addAll(fiAdvResponse.getSearchResult().getItem());
				pageNumber++;
			}
		}

		return items;
	}

	public List<SearchItem> getItemsByKeywordCategory(String keyword, String categoryId) {
		return this.getItemsByKeywordCategoryAndPrice(keyword, categoryId, -1, -1);
	}

	public List<SearchItem> getItemsByKeyword(String keyword) {
		return this.getItemsByKeywordCategoryAndPrice(keyword, null, -1, -1);
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

	@Override
	public List<CategoryType> getMainCategories() {
		GetCategoriesCall categoriesCall = new GetCategoriesCall(eBaySoapApi);
		categoriesCall.setCategorySiteID(SITE_CODING);
		categoriesCall.addDetailLevel(DetailLevelCodeType.RETURN_ALL);
		categoriesCall.setLevelLimit(1);

		CategoryType[] categories = null;
		try {
			categories = categoriesCall.getCategories();
		} catch (SdkException e) {
			logger.error(e.getMessage());
		} catch (Exception e) {
			logger.error(e.getMessage());
		}

		return Arrays.asList(categories);
	}

	@Override
	public List<CategoryType> getSubCategories(String parentCategoryId) {
		GetCategoriesCall categoriesCall = new GetCategoriesCall(eBaySoapApi);
		CategoryType parentCategory = getCategoryById(parentCategoryId);
		if (parentCategory == null) {
			System.out.println("No such category");
			return new ArrayList<>();
		}

		categoriesCall.setCategorySiteID(SITE_CODING);
		categoriesCall.addDetailLevel(DetailLevelCodeType.RETURN_ALL);
		categoriesCall.setLevelLimit(parentCategory.getCategoryLevel() + 1);
		categoriesCall.setParentCategory(new String[] { parentCategory.getCategoryID() });

		CategoryType[] categories = null;
		try {
			categories = categoriesCall.getCategories();
		} catch (SdkException e) {
			logger.error(e.getMessage());
		} catch (Exception e) {
			logger.error(e.getMessage());
		}

		return Arrays.asList(categories);
	}
}
