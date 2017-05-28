package com.project.pik.EbayApi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.project.pik.EbayApi.daos.Offer;
import com.project.pik.EbayApi.daos.OfferRepository;

public class OfferServiceImpl implements OfferService{

	@Autowired
	private OfferRepository offerRepository;
	
	@Override
	public void save(Offer offer) {
		offerRepository.save(offer);
		
	}

	@Override
	public List<Offer> findByUserName(String name) {
		return offerRepository.findByOrderUserName(name);
	}

}
