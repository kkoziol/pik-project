package com.project.pik.EbayApi.controller;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ebay.services.finding.Category;
import com.ebay.services.finding.SearchItem;
import com.ebay.soap.eBLBaseComponents.CategoryType;
import com.project.pik.EbayApi.service.EbayCategoriesService;
import com.project.pik.EbayApi.service.EbayItemsService;

@RestController
@RequestMapping("/ebay")
public class EbayController {
	private static final Logger logger = Logger.getLogger(EbayController.class);
	@Autowired
	private EbayCategoriesService ebayCategoriesService;
	@Autowired
	private EbayItemsService ebayItemsService;

	@RequestMapping(value = "/categories/maincategories", method = RequestMethod.GET)
	public ResponseEntity<List<CategoryType>> listMainCategories() {
		List<CategoryType> categories = ebayCategoriesService.getMainCategories();
		if (categories == null || categories.isEmpty()) {
			logger.error("Cannot receive main categories");
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}

		return new ResponseEntity<>(categories, HttpStatus.OK);
	}

	@RequestMapping(value = "/categories/subcategories/{parentId}", method = RequestMethod.GET)
	public ResponseEntity<List<CategoryType>> listSubCategories(@PathVariable("parentId") String parentId) {
		List<CategoryType> subCategories = ebayCategoriesService.getSubCategories(parentId);
		if (subCategories == null || subCategories.isEmpty()) {
			logger.error("Cannot receive subcategories of " + parentId);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}

		return new ResponseEntity<>(subCategories, HttpStatus.OK);
	}

	@RequestMapping(value = "/categories/specifics/{categoryId}", method = RequestMethod.GET)
	public ResponseEntity<Map<String, List<String>>> listCategorySpecifics(
			@PathVariable("categoryId") String categoryId) {
		Map<String, List<String>> specifics = ebayCategoriesService.getCategorySpecificsByCategoryId(categoryId);
		if (specifics == null || specifics.isEmpty()) {
			logger.error("Cannot specifics of category: " + categoryId);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}

		return new ResponseEntity<>(specifics, HttpStatus.OK);
	}

	@RequestMapping(value = "/categories/bestmatch/{keyword}", method = RequestMethod.GET)
	public ResponseEntity<Category> searchBestMachingCategoryForKeyword(@PathVariable("keyword") String keyword) {
		Category category = ebayCategoriesService.getBestMatchCategory(keyword);
		if (category == null) {
			logger.error("Cannot receive best matching category of " + keyword);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}

		return new ResponseEntity<>(category, HttpStatus.OK);
	}

	@RequestMapping(value = { "/items/search/{keyword}/{categoryId}/{minPrice}/{maxPrice}/{pageNumber}",
			"/items/search/{keyword}/{categoryId}/{pageNumber}",
			"/items/search/{keyword}/{pageNumber}" }, method = RequestMethod.GET)
	public ResponseEntity<List<SearchItem>> searchItemsByKeywordCategoryAndPrice(
			@PathVariable Map<String, String> pathVariables) {
		List<SearchItem> items;
		if (pathVariables.containsKey("minPrice") && pathVariables.containsKey("maxPrice")) {
			items = ebayItemsService.getItemsByKeywordCategoryAndPrice(pathVariables.get("keyword"),
					pathVariables.get("categoryId"), Integer.parseInt(pathVariables.get("minPrice")),
					Integer.parseInt(pathVariables.get("maxPrice")), Integer.parseInt(pathVariables.get("pageNumber")));
		} else if (pathVariables.containsKey("categoryId")) {
			items = ebayItemsService.getItemsByKeywordCategory(pathVariables.get("keyword"),
					pathVariables.get("categoryId"), Integer.parseInt(pathVariables.get("pageNumber")));
		} else {
			items = ebayItemsService.getItemsByKeyword(pathVariables.get("keyword"),
					Integer.parseInt(pathVariables.get("pageNumber")));
		}
		if (items == null || items.isEmpty()) {
			StringBuilder msg = new StringBuilder("Cannot receive search result of ");
			msg.append("/" + pathVariables.get("keyword"));
			if (pathVariables.containsKey("categoryId"))
				msg.append("/" + pathVariables.get("categoryId"));
			if (pathVariables.containsKey("minPrice") && pathVariables.containsKey("maxPrice"))
				msg.append("/" + pathVariables.get("minPrice") + "/" + pathVariables.get("maxPrice"));
			logger.error(msg.toString());

			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}

		return new ResponseEntity<>(items, HttpStatus.OK);
	}

	@RequestMapping(value = "/items/bestmatch/{keyword}", method = RequestMethod.GET)
	public ResponseEntity<SearchItem> searchBestMachingItemForKeyword(@PathVariable("keyword") String keyword) {
		SearchItem item = ebayItemsService.getBestMatchItem(keyword);
		if (item == null) {
			logger.error("Cannot receive best matching category of " + keyword);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}

		return new ResponseEntity<>(item, HttpStatus.OK);
	}

	@RequestMapping(value = "/items/cheapest/{keyword}/{categoryId}", method = RequestMethod.GET)
	public ResponseEntity<SearchItem> searchCheapestItemInCategory(@PathVariable("keyword") String keyword,
			@PathVariable("categoryId") String categoryId) {
		SearchItem item = ebayItemsService.getCheapestItemByKeywordAndCategory(keyword, categoryId);
		if (item == null) {
			logger.error("Cannot receive cheapest item of " + keyword + "/" + categoryId);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}

		return new ResponseEntity<>(item, HttpStatus.OK);
	}
}
