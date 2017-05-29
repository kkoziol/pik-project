package com.project.pik.EbayApi.service;

import java.util.List;

import com.ebay.services.finding.SearchItem;

public interface EbayItemsService {
	List<SearchItem> getItemsByKeyword(String keyword,int pageNumber);

	List<SearchItem> getItemsByKeywordCategory(String keyword, String categoryId,int pageNumber);

	List<SearchItem> getItemsByKeywordCategoryAndPrice(String keyword, String categoryId, int minPrice, int maxPrice,int pageNumber);

	SearchItem getBestMatchItem(String keyword);

	SearchItem getCheapestItemByKeywordAndCategory(String keyword, String categoryId);
}
