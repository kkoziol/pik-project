package com.project.pik.EbayApi.service;

import java.util.List;
import java.util.Map;

import com.ebay.services.finding.Category;
import com.ebay.soap.eBLBaseComponents.CategoryType;

public interface EbayCategoriesService {
	List<CategoryType> getMainCategories();

	List<CategoryType> getSubCategories(String parentCategoryId);

	Map<String, List<String>> getCategorySpecificsByCategoryId(String categoryId);

	Category getBestMatchCategory(String keyword);
}
