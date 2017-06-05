package com.project.pik.EbayView.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.project.pik.EbayView.jsonEntities.User;
import com.project.pik.EbayView.services.UserService;


@RestController
public class RegisterController {
	
	@Autowired
	UserService userService;

	@RequestMapping(value = "/register", method = RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<User> register(@RequestBody User user) {
		if(userService.registerUser(user))
			return new ResponseEntity<User>(user, HttpStatus.OK);
		else{
			return new ResponseEntity<User>(user, HttpStatus.BAD_REQUEST);
		}
	}


}
