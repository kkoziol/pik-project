package com.project.pik.EbayApi.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.project.pik.EbayApi.daemon.SearchEbayOffersDaemon;
import com.project.pik.EbayApi.model.Order;
import com.project.pik.EbayApi.model.User;
import com.project.pik.EbayApi.model.UserPreference;
import com.project.pik.EbayApi.repositories.OrderRepository;
import com.project.pik.EbayApi.repositories.UserRepository;

@RestController
@RequestMapping("/orders")
public class OrderController {
	private static final Logger logger = Logger.getLogger(OrderController.class);
	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	
	@ResponseBody 
	@RequestMapping(value = "/list/{username}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<List<Map<String,Object>>> getOrdersPerUser(@PathVariable String username){
		List<Order> orders = orderRepository.findByUserLogin(username);
		
		if (orders == null || orders.isEmpty()) {
			logger.error("Cannot receive orders for user " + username);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		List<Map<String,Object>> result = new ArrayList<>();
		ObjectMapper jsonMapper = new ObjectMapper();
		for(Order o : orders) {
			try {
				Map<String,Object> map = new HashMap<>();
				map.put("orderId",o.getOrderId());
				map.put("userPreference", jsonMapper.readValue(o.getPreferencesAsJson(), UserPreference.class));
				result.add(map);
			} catch (IOException e) {
				logger.error(e.getMessage());
			}
		}
		
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/add/{username}", method = RequestMethod.POST)
	public ResponseEntity<Order> addOrderForUser(@PathVariable String username, @RequestBody UserPreference preference){
		Order order = new Order();
		preference.setDateAndTime(new java.sql.Date(Calendar.getInstance().getTime().getTime()));
		User user = userRepository.findOneByLogin(username);
		order.setUser(user);
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
	
	@RequestMapping(value="/delete/{orderId}",method = RequestMethod.GET,produces = "application/json")
	public ResponseEntity<String> findByUser(@PathVariable int orderId) {
		Integer deletedId = orderRepository.deleteByOrderId(orderId);
		return new ResponseEntity<>("{\"deletedOrderId\":"+deletedId+"}", HttpStatus.OK);
	}
}
