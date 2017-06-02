package com.project.pik.EbayApi.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.project.pik.EbayApi.model.FoundResult;
import com.project.pik.EbayApi.model.Order;

@Repository
public interface FoundResultRepository extends JpaRepository<FoundResult,Long>{
	public List<FoundResult> findByOrderUserLogin(String name);

	public List<FoundResult> findByOrder(Order order);
	
	@Transactional
    Long deleteByFoundResultId(Long firstName);
}
