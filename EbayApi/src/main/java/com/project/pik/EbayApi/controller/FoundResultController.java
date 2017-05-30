package com.project.pik.EbayApi.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import com.project.pik.EbayApi.daemon.SearchEbayOffersDaemon;
import com.project.pik.EbayApi.model.FoundResult;
import com.project.pik.EbayApi.repositories.FoundResultRepository;

@RestController
@RequestMapping("/foundresults")
public class FoundResultController {
	private static final Logger logger = Logger.getLogger(FoundResultController.class);

	@Autowired
	private FoundResultRepository foundResultRepository;

	@RequestMapping("/list/{username}")
	public ResponseEntity<List<FoundResult>> findByUser(@PathVariable String username) {
		List<FoundResult> results = foundResultRepository.findByOrderUserLogin(username);

		if (results == null || results.isEmpty()) {
			logger.error("Cannot receive found results for user " + username);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}

		return new ResponseEntity<>(results, HttpStatus.OK);
	}

	@RequestMapping(value = "/async/{username}", method = RequestMethod.GET)
	@ResponseBody
	@Async
	public DeferredResult<List<FoundResult>> findByUserAsync(@PathVariable String username) {
		DeferredResult<List<FoundResult>> deferredResult = new DeferredResult<>(5000000L, new ArrayList<>());
		SearchEbayOffersDaemon.getInstance().registerListener(username, deferredResult);
		return deferredResult;
	}
}
