package com.project.pik.EbayApi.controller;

import java.util.Calendar;
import java.util.List;

import javax.persistence.EntityManager;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.pik.EbayApi.model.Order;
import com.project.pik.EbayApi.model.User;
import com.project.pik.EbayApi.model.UserPreference;
import com.project.pik.EbayApi.repositories.OrderRepository;

@RestController
@RequestMapping("/orders")
public class OrderController {
	private static final Logger logger = Logger.getLogger(OrderController.class);
	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private EntityManager entityManager;
	
	
	@ResponseBody 
	@RequestMapping(value = "/list/{username}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<List<Order>> getOrdersPerUser(@PathVariable String username){
		List<Order> orders = orderRepository.findByUserName(username);
		
		if (orders == null || orders.isEmpty()) {
			logger.error("Cannot receive orders for user " + username);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}

		return new ResponseEntity<>(orders, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/add/{username}", method = RequestMethod.POST)
	public ResponseEntity<Order> addOrderForUser(@PathVariable String username, @RequestBody UserPreference preference){
		Order order = new Order();
		order.setDate(new java.sql.Date(Calendar.getInstance().getTime().getTime()));
		order.setUser(entityManager.getReference(User.class, username));
		order.setHistoryLog(false);
		
		
		ObjectMapper jsonMapper = new ObjectMapper();
		try {
			String preferenceString = jsonMapper.writeValueAsString(preference);
			order.setPreferencesAsJson(preferenceString);
		} catch (JsonProcessingException e) {
			logger.error("JSON parsing failed");
			logger.error(e.getMessage());
			
			return new ResponseEntity<>(order, HttpStatus.BAD_REQUEST);
		}
		
		
		orderRepository.save(order);
		
		return new ResponseEntity<>(order, HttpStatus.OK);
	}
}
