package com.project.pik.EbayApi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.pik.EbayApi.model.FoundResult;
import com.project.pik.EbayApi.repositories.FoundResultRepository;

@Service
public class FoundResultService {

	@Autowired
	private FoundResultRepository foundResultRepository;
	
	public List<FoundResult> findByUserName(String name) {
		return foundResultRepository.findByOrderUserName(name);
	}

}
