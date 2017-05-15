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
import com.project.pik.EbayApi.service.EbayService;

@RestController
@RequestMapping("/items")
public class EbayItemsController {
	private static final Logger logger = Logger.getLogger(EbayItemsController.class);
	@Autowired
	EbayService ebayService;

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
	public ResponseEntity<List<SearchItem>> searchItemsByKeywordCategoryAndPrice(
			@PathVariable("keyword") String keyword, @PathVariable("categoryId") String categoryId,
			@PathVariable("minPrice") String minPrice, @PathVariable("maxPrice") String maxPrice) {
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

}
