package com.project.pik.EbayApi.daemon;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.async.DeferredResult;

import com.ebay.services.client.ClientConfig;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.pik.EbayApi.model.FoundResult;

@RunWith(MockitoJUnitRunner.class)
// TODO - not implemented
public class SearchEbayOffersDaemonTest {
	// TODO - dac loggera o scopie test

	private final static String TEST_PREF_AS_JSON = prepareCategorySpecificsAsJson();
	
	@Mock
	Map<String, DeferredResult<List<FoundResult>>> registeredListeners =  new HashMap<>();
	
	@InjectMocks
	private SearchEbayOffersDaemon daemon =  SearchEbayOffersDaemon.getInstance();
	
	
	
//	@InjectMocks
//	private FoundResultRepository foundResultRepository;

	@Autowired
	private ClientConfig eBayClientConfig;
	
	@Before
	public void setup(){
		MockitoAnnotations.initMocks(this);
		registeredListeners =  new HashMap<>();
		registeredListeners.put("somestring", new DeferredResult<>(10000L, "timeoutresult"));
	}
	
	@After
	public void cleanup() {
		
	}
	
	@Test
	public void testSingleton(){
		daemon.start();
		try {
			Thread.sleep(50000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

	
	@Test
	public void testAsyncFoundMailNotification(){
		
	}
	
	@Test
	public void testAsyncFoundHttpResponse(){
		
	}
	
	@Test
	public void testSearchAccuracy(){
		
	}
	
	private static String prepareCategorySpecificsAsJson(){
	    Map<String, Set<String>> categorySpecifics = new HashMap<>(); 
	    Set<String> set = new HashSet<>(); 
	    set.add("1TB"); 
	    categorySpecifics.put("Hard Drive Capacity", set); 
	    set = new HashSet<>(); 
	    set.add("Keyboard"); 
	    set.add("Mouse"); 
	    categorySpecifics.put("Bundled Items", set); 
	    set = new HashSet<>(); 
	    set.add("27 inches"); 
	    categorySpecifics.put("Screen Size", set); 
	    set = new HashSet<>(); 
	    set.add("Intel Core i5 6th Gen."); 
	    categorySpecifics.put("Processor Type", set); 
	    set = new HashSet<>(); 
	    set.add("3.30GHz"); 
	    categorySpecifics.put("Processor Speed", set);
	    
	    ObjectMapper jsonMapper = new ObjectMapper();
	    try {
			String json = jsonMapper.writeValueAsString(categorySpecifics);
			return json;
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
	    return "";
	}
	
	

}
