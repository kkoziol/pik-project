package com.project.pik.EbayApi.service;

import java.util.Calendar;
import java.util.List;

import com.ebay.services.finding.SearchItem;
import com.ebay.soap.eBLBaseComponents.CategoryType;

public interface EbayService {
	public Calendar getEbayTime();
	public List<SearchItem> getItemsByKeywordCategoryAndPrice(String keyword, String categoryId, int minPrice, int maxPrice);
	public SearchItem getCheapestItemByKeywordAndCategory(String keyword, String categoryId);
	public List<CategoryType> getMainCategories();	
	public List<CategoryType> getSubCategories(String parentCategoryId);
}
