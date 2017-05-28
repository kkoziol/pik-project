package com.project.pik.EbayApi.daos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.pik.EbayApi.model.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long>{
	public List<Order> findByUserName(String login); 
}
