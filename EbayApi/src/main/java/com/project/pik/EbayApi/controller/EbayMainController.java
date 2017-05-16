package com.project.pik.EbayApi.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EbayMainController {

	@RequestMapping(value = "/api/test", method = RequestMethod.GET, produces = "application/json")
	  public Map<String,Object> home() {
	    Map<String,Object> model = new HashMap<String,Object>();
	    model.put("id", UUID.randomUUID().toString());
	    model.put("content", "Authenticated, but stil in progress 3===o");
	    return model;
	  }
}
