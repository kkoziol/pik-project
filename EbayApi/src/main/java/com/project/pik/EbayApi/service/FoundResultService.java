package com.project.pik.EbayApi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.pik.EbayApi.daos.FoundResultRepository;
import com.project.pik.EbayApi.model.FoundResult;

@Service
public class FoundResultService {

	@Autowired
	private FoundResultRepository foundResultRepository;
	
	public List<FoundResult> findByUserName(String name) {
		return foundResultRepository.findByOrderUserName(name);
	}

}
