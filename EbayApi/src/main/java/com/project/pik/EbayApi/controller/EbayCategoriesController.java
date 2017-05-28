package com.project.pik.EbayApi.controller;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ebay.services.finding.Category;
import com.ebay.soap.eBLBaseComponents.CategoryType;
import com.project.pik.EbayApi.mail.MailSender;
import com.project.pik.EbayApi.model.Email;
import com.project.pik.EbayApi.service.EbayService;

@RestController
@RequestMapping("/categories")
public class EbayCategoriesController {
	private static final Logger logger = Logger.getLogger(EbayCategoriesController.class);
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

	@RequestMapping(value = "/categoryspecifics/{categoryId}", method = RequestMethod.GET)
	public ResponseEntity<Map<String, List<String>>> listCategorySpecifics(
			@PathVariable("categoryId") String categoryId) {
		Map<String, List<String>> specifics = ebayService.getCategorySpecificsByCategoryId(categoryId);
		if (specifics == null || specifics.isEmpty()) {
			logger.error("Cannot specifics of category: " + categoryId);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}

		return new ResponseEntity<>(specifics, HttpStatus.OK);
	}

	@RequestMapping(value = "/bestmatch/{keyword}", method = RequestMethod.GET)
	public ResponseEntity<Category> searchBestMachingCategoryForKeyword(@PathVariable("keyword") String keyword) {
		Category category = ebayService.getBestMatchCategory(keyword);
		if (category == null) {
			logger.error("Cannot receive best matching category of " + keyword);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}

		return new ResponseEntity<>(category, HttpStatus.OK);
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
