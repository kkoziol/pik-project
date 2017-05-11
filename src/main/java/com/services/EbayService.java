package com.services;

import java.util.Calendar;
import java.util.List;

import com.ebay.services.finding.SearchItem;
import com.ebay.soap.eBLBaseComponents.CategoryType;

public interface EbayService {
	public Calendar getEbayTime();
	public List<SearchItem> getItemsByKeywordCategoryAndPrice(String keyWord, String categoryId, int minPrice, int maxPrice);
	public List<CategoryType> getMainCategories();	
	public List<CategoryType> getSubCategories(String parentCategoryId);
}
