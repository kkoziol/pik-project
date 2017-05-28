package com.project.pik.EbayApi.controller;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.project.pik.EbayApi.daos.OrderRepository;
import com.project.pik.EbayApi.model.Order;
import com.project.pik.EbayApi.model.User;

@Controller
@RequestMapping("/orders")
public class EbayOrderController {
	
	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private EntityManager entityManager;
	
	@RequestMapping(value = "/{username}", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody List<Order> getOrdersPerUser(@PathVariable String username){
		return orderRepository.findByUserName(username);
	}
	
	@RequestMapping(value = "/{username}", method = RequestMethod.POST)
	public void addOrderForUser(@PathVariable String username, @RequestBody Order order){
		User user = entityManager.getReference(User.class, username);
		order.setUser(user);
		orderRepository.save(order);
	}
	
	
	
}
