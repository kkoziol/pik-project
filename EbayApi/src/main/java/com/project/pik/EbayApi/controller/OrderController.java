package com.project.pik.EbayApi.controller;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.project.pik.EbayApi.model.Order;
import com.project.pik.EbayApi.model.User;
import com.project.pik.EbayApi.repositories.OrderRepository;
import com.project.pik.EbayApi.service.EbayCategoriesService;

@RestController
@RequestMapping("/orders")
public class OrderController {
	
	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private EntityManager entityManager;
	
	@Autowired
	private EbayCategoriesService ebayCategoriesService;
	
	@ResponseBody 
	@RequestMapping(value = "/{username}", method = RequestMethod.GET, produces = "application/json")
	public List<Order> getOrdersPerUser(@PathVariable String username){
		return orderRepository.findByUserName(username);
	}
	
	@RequestMapping(value = "/{username}", method = RequestMethod.POST)
	public void addOrderForUser(@PathVariable String username, @RequestBody Order order){
		User user = entityManager.getReference(User.class, username);
		order.setUser(user);
		orderRepository.save(order);
	}
	
	@ResponseBody
	@RequestMapping(value = "/categorySpecifics/{categoryId}", method = RequestMethod.GET)
	public Map<String, List<String>> getCategorySpecificsByCategoryId(@PathVariable String categoryId){
		return ebayCategoriesService.getCategorySpecificsByCategoryId(categoryId);
	}
}
