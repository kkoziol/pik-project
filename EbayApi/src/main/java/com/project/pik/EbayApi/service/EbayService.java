package com.project.pik.EbayApi.service;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

import com.ebay.services.finding.Category;
import com.ebay.services.finding.SearchItem;
import com.ebay.soap.eBLBaseComponents.CategoryType;

public interface EbayService {
	/** Ebay mata-data */
	public Calendar getEbayTime();

	/** Categories meta-data */
	public List<CategoryType> getMainCategories();

	public List<CategoryType> getSubCategories(String parentCategoryId);

	public Map<String, List<String>> getCategorySpecificsByCategoryId(String categoryId);

	public Category getBestMatchCategory(String keyword);

	public CategoryType getCategoryById(String categoryId);

	
	/** Item finding */
	public List<SearchItem> getItemsByKeyword(String keyword);

	public List<SearchItem> getItemsByKeywordCategory(String keyword, String categoryId);

	public List<SearchItem> getItemsByKeywordCategoryAndPrice(String keyword, String categoryId, int minPrice,
			int maxPrice);

	public SearchItem getBestMatchItem(String keyword);

	public SearchItem getCheapestItemByKeywordAndCategory(String keyword, String categoryId);
}
