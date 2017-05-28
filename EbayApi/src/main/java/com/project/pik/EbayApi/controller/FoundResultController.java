package com.project.pik.EbayApi.controller;

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
import com.project.pik.EbayApi.daos.FoundResultRepository;
import com.project.pik.EbayApi.model.FoundResult;

@RestController
@RequestMapping("/offers")
public class FoundResultController {

	@Autowired
	private FoundResultRepository offerRepository;
	
	@RequestMapping("/{username}")
	public List<FoundResult> findByUser(@PathVariable String username){
		return offerRepository.findByOrderUserName(username);
	}
	
	@RequestMapping(value = "/async/{username}", method = RequestMethod.GET)
	@ResponseBody
	@Async
	public DeferredResult<List<FoundResult>> findByUserAsync(@PathVariable String username){
		DeferredResult<List<FoundResult>> deferredResult = new DeferredResult<>();
		SearchEbayOffersDaemon.getInstance().registerListener(username, deferredResult);
		return deferredResult;
	}
}
