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
import com.project.pik.EbayApi.daos.Offer;
import com.project.pik.EbayApi.daos.OfferRepository;

@RestController
@RequestMapping("/offers")
public class EbayOfferController {

	@Autowired
	private OfferRepository offerRepository;
	
	@RequestMapping(value = "/find/{username}", method = RequestMethod.GET)
	public List<Offer> findByUser(@PathVariable String username){
		return offerRepository.findByOrderUserName(username);
	}
	
//	@RequestMapping(value = "/async/{username}", method = RequestMethod.GET)
//	@Async
//	public Future<List<Offer>> findByUserAsync(@PathVariable String username){
//		SearchDaemonFutureResult toReturn = new SearchDaemonFutureResult();
//		SearchEbayOffersDaemon.getInstance().registerListener(username, toReturn);
//		return toReturn;
//	}
	
	@RequestMapping(value = "/async/{username}", method = RequestMethod.GET)
	@ResponseBody
	@Async
	public DeferredResult<List<Offer>> findByUserAsync(@PathVariable String username){
		DeferredResult<List<Offer>> deferredResult = new DeferredResult<>();
		SearchEbayOffersDaemon.getInstance().registerListener(username, deferredResult);
		return deferredResult;
	}
}
