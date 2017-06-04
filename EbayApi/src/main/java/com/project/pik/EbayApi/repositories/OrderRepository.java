package com.project.pik.EbayApi.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.project.pik.EbayApi.model.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long>{
	List<Order> findByUserLogin(String login); 
	
	List<Order> findByIsHistoryLog(boolean isHistoryLog);
	
	@Transactional
    Integer deleteByOrderId(int orderId);
}
