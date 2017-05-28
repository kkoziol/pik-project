package com.project.pik.EbayApi.service;

import java.util.List;

import com.project.pik.EbayApi.daos.Offer;

public interface OfferService {

	void save(Offer offer);

	List<Offer> findByUserName(String name);

}
