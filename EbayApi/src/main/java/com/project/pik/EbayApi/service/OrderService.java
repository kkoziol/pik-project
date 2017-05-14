package com.project.pik.EbayApi.service;

import java.util.List;

import com.project.pik.EbayApi.daos.Order;

public interface OrderService {
	List<Order> listActiveOrders();
}
