package com.project.pik.EbayApi.daos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.pik.EbayApi.model.FoundResult;

public interface FoundResultRepository extends JpaRepository<FoundResult,Long>{
	public List<FoundResult> findByOrderUserName(String name);
}
