package com.project.pik.EbayApi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.project.pik.EbayApi.daos.FoundResultRepository;
import com.project.pik.EbayApi.model.FoundResult;

@Controller
@RequestMapping("/offers")
public class EbayOfferController {

	@Autowired
	private FoundResultRepository offerRepository;
	
	@RequestMapping("/{username}")
	public List<FoundResult> findByUser(@PathVariable String username){
		return offerRepository.findByOrderUserName(username);
	}
}
