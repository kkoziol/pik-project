package com.project.pik.EbayApi.service;

import java.util.List;

import com.project.pik.EbayApi.model.Order;

public interface OrderService {
	List<Order> listActiveOrders();
}
