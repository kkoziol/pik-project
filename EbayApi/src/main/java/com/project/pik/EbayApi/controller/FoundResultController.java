package com.project.pik.EbayApi.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

	@Autowired
	private FoundResultRepository foundResultRepository;
	
	@RequestMapping("/{username}")
	public List<FoundResult> findByUser(@PathVariable String username){
		return foundResultRepository.findByOrderUserName(username);
	}
	
	@RequestMapping(value = "/async/{username}", method = RequestMethod.GET)
	@ResponseBody
	@Async
	public DeferredResult<List<FoundResult>> findByUserAsync(@PathVariable String username){
		DeferredResult<List<FoundResult>> deferredResult = new DeferredResult<>(500000L, new ArrayList<>());
		SearchEbayOffersDaemon.getInstance().registerListener(username, deferredResult);
		return deferredResult;
	}
}
