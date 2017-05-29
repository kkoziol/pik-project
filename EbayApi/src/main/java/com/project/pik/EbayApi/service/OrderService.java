package com.project.pik.EbayApi.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import com.project.pik.EbayApi.daos.OrderRepository;
import com.project.pik.EbayApi.model.Order;

public class OrderService{

	@Autowired
	private OrderRepository orderRepository;

	public List<Order> listActiveOrders() {
		List<Order> allOrders = orderRepository.findAll();
		return allOrders.stream().filter(e -> !e.isHistoryLog()).collect(Collectors.toList());
	}

}
