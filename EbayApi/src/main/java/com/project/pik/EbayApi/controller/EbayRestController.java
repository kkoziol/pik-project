package com.project.pik.EbayApi.controller;

import java.util.List;

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
import com.project.pik.EbayApi.service.EbayService;

@RestController
public class EbayRestController {
	private static final Logger logger = Logger.getLogger(EbayRestController.class);
	@Autowired
	EbayService ebayService;

	@RequestMapping(value = "/maincategories", method = RequestMethod.GET)
	public ResponseEntity<List<CategoryType>> listMainCategories() {
		List<CategoryType> categories = ebayService.getMainCategories();
		if (categories == null || categories.isEmpty()) {
			logger.error("Cannot receive main categories");
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}

		return new ResponseEntity<>(categories, HttpStatus.OK);
	}

	@RequestMapping(value = "/subcategories/{parentId}", method = RequestMethod.GET)
	public ResponseEntity<List<CategoryType>> listSubCategories(@PathVariable("parentId") String parentId) {
		List<CategoryType> subCategories = ebayService.getSubCategories(parentId);
		if (subCategories == null || subCategories.isEmpty()) {
			logger.error("Cannot receive subcategories of " + parentId);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}

		return new ResponseEntity<>(subCategories, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/search/{keyword}", method = RequestMethod.GET)
	public ResponseEntity<List<SearchItem>> searchItemsByKeyword(@PathVariable("keyword") String keyword) {
		List<SearchItem> items = ebayService.getItemsByKeyword(keyword);
		if (items == null || items.isEmpty()) {
			logger.error("Cannot receive search result of " + keyword);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}

		return new ResponseEntity<>(items, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/search/{keyword}/{categoryId}", method = RequestMethod.GET)
	public ResponseEntity<List<SearchItem>> searchItemsByKeywordAndCategory(@PathVariable("keyword") String keyword,
			@PathVariable("categoryId") String categoryId) {
		List<SearchItem> items = ebayService.getItemsByKeywordCategory(keyword, categoryId);
		if (items == null || items.isEmpty()) {
			logger.error("Cannot receive search result of " + keyword + "/" + categoryId);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}

		return new ResponseEntity<>(items, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/search/{keyword}/{categoryId}/{minPrice}/{maxPrice}", method = RequestMethod.GET)
	public ResponseEntity<List<SearchItem>> searchItemsByKeywordCategoryAndPrice(@PathVariable("keyword") String keyword,
			@PathVariable("categoryId") String categoryId, @PathVariable("minPrice") String minPrice,
			@PathVariable("maxPrice") String maxPrice) {
		List<SearchItem> items = ebayService.getItemsByKeywordCategoryAndPrice(keyword, categoryId, 
				Integer.parseInt(minPrice), Integer.parseInt(maxPrice));
		if (items == null || items.isEmpty()) {
			logger.error("Cannot receive search result of " + keyword + "/" + categoryId + "/min:" + minPrice + "/max:"
					+ maxPrice);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}

		return new ResponseEntity<>(items, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/bestmatch/item/{keyword}", method = RequestMethod.GET)
	public ResponseEntity<Category> searchBestMachingItemForKeyword(@PathVariable("keyword") String keyword) {
		SearchItem item = ebayService.getBestMatchItem(keyword);
		if (item == null) {
			logger.error("Cannot receive best matching category of " + keyword);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}

		return new ResponseEntity<>(item.getPrimaryCategory(), HttpStatus.OK);
	}
	

	@RequestMapping(value = "/bestmatch/category/{keyword}", method = RequestMethod.GET)
	public ResponseEntity<Category> searchBestMachingCategoryForKeyword(@PathVariable("keyword") String keyword) {
		SearchItem item = ebayService.getBestMatchItem(keyword);
		if (item == null) {
			logger.error("Cannot receive best matching category of " + keyword);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}

		return new ResponseEntity<>(item.getPrimaryCategory(), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/cheapest/item/{keyword}/{categoryId}", method = RequestMethod.GET)
	public ResponseEntity<SearchItem> searchCheapestItemInCategory(@PathVariable("keyword") String keyword,
			@PathVariable("categoryId") String categoryId) {
		SearchItem item = ebayService.getCheapestItemByKeywordAndCategory(keyword, categoryId);
		if (item == null) {
			logger.error("Cannot receive cheapest item of " + keyword + "/" + categoryId);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}

		return new ResponseEntity<>(item, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/categoryname/{categoryId}", method = RequestMethod.GET)
	public ResponseEntity<String> searchCategoryNameById(@PathVariable("categoryId") String categoryId) {
		CategoryType category = ebayService.getCategoryById(categoryId);
		if (category == null) {
			logger.error("Cannot receive category from category Id: " + categoryId);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}

		return new ResponseEntity<>(category.getCategoryName(), HttpStatus.OK);
	}

}
