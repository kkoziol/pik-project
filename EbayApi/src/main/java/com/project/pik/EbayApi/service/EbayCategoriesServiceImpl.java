package com.project.pik.EbayApi.service;

import static com.project.pik.EbayApi.consts.ApiConsts.SITE_CODING;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.ebay.sdk.ApiContext;
import com.ebay.sdk.SdkException;
import com.ebay.sdk.call.GetCategoriesCall;
import com.ebay.sdk.call.GetCategorySpecificsCall;
import com.ebay.services.client.ClientConfig;
import com.ebay.services.client.FindingServiceClientFactory;
import com.ebay.services.finding.Category;
import com.ebay.services.finding.FindItemsAdvancedRequest;
import com.ebay.services.finding.FindItemsAdvancedResponse;
import com.ebay.services.finding.FindingServicePortType;
import com.ebay.services.finding.PaginationInput;
import com.ebay.services.finding.SearchItem;
import com.ebay.services.finding.SortOrderType;
import com.ebay.soap.eBLBaseComponents.CategoryType;
import com.ebay.soap.eBLBaseComponents.DetailLevelCodeType;
import com.ebay.soap.eBLBaseComponents.NameRecommendationType;
import com.ebay.soap.eBLBaseComponents.RecommendationsType;
import com.ebay.soap.eBLBaseComponents.ValueRecommendationType;

public class EbayCategoriesServiceImpl implements EbayCategoriesService {

	@Autowired
	private ApiContext eBaySoapApi;

	@Autowired
	private ClientConfig eBayClientConfig;

	/** LOGGER */
	private static final Logger logger = Logger.getLogger(EbayCategoriesServiceImpl.class);

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
			logger.error("No such category");
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

	@Override
	public Map<String, List<String>> getCategorySpecificsByCategoryId(String categoryId) {
		Map<String, List<String>> toReturn = new HashMap<>();
		GetCategorySpecificsCall call = new GetCategorySpecificsCall();
		call.setApiContext(eBaySoapApi);
		call.setCategoryID(new String[] { categoryId });
		RecommendationsType[] categorySpecifics = null;
		try {
			categorySpecifics = call.getCategorySpecifics();
		} catch (SdkException e) {
			logger.error(e.getMessage());
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		if (categorySpecifics == null || categorySpecifics.length == 0)
			return toReturn;

		NameRecommendationType[] nameRecommendation = categorySpecifics[0].getNameRecommendation();
		for (NameRecommendationType nrt : nameRecommendation) {
			List<String> val = new ArrayList<>();
			for (ValueRecommendationType vrt : nrt.getValueRecommendation()) {
				val.add(vrt.getValue());
			}
			toReturn.put(nrt.getName(), val);
		}
		return toReturn;
	}

	@Override
	public Category getBestMatchCategory(String keyword) {
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
			return fiAdvResponse.getSearchResult().getItem().get(0).getPrimaryCategory();

		return new SearchItem().getPrimaryCategory();
	}

	private CategoryType getCategoryById(String categoryId) {
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

}
