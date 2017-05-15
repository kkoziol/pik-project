package com.project.pik.EbayApi.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.pik.EbayApi.daos.Order;
import com.project.pik.EbayApi.daos.OrderRepository;

@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	private OrderRepository orderRepository;

	@Override
	public List<Order> listActiveOrders() {
		List<Order> allOrders = orderRepository.findAll();
		return allOrders.stream().filter(e -> !e.isHistoryLog()).collect(Collectors.toList());
	}

}
