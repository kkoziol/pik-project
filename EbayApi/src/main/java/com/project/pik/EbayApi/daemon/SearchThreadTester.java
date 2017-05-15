package com.project.pik.EbayApi.daemon;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.junit.Test;

public class SearchThreadTester {
	
	/**
	 * Category: Computers/Tablets & NetworkingDesktops & All-In-One ComputersApple Desktops & All-In-One Computers
	 */
	String desktopAndAllInOneComputersCategorieId = "111418";
	
	/** LOGGER */
	private static final Logger logger = Logger.getLogger(SearchThreadTester.class);
	
	@Test
	public void searchForSinglePreferenceTest1(){
		UserPreference preferences = new UserPreference();
		preferences.setCategoryId(desktopAndAllInOneComputersCategorieId);
		preferences.setPrizeMax("1200");
		preferences.setRefinmentsAsSet(createRefinments());
		SearchThread searchThread = new SearchThread();
		List<String> searchForSinglePreference = searchThread.searchForSinglePreference(preferences);
		StringBuilder builder = new StringBuilder();
		for(String s : searchForSinglePreference){
			builder.append(s + "\n");
		}
		
		logger.debug("Found urls: " + builder.toString());
	}
	
	private Map<String, Set<String>> createRefinments(){
		Map<String, Set<String>> refinments = new HashMap<>();
		Set<String> set = new HashSet<>();
		set.add("1TB");
		refinments.put("Hard Drive Capacity", set);
		set = new HashSet<>();
		set.add("Keyboard");
		set.add("Mouse");
		refinments.put("Bundled Items", set);
		set = new HashSet<>();
		set.add("27 inches");
		refinments.put("Screen Size", set);
		set = new HashSet<>();
		set.add("Intel Core i5 6th Gen.");
		refinments.put("Processor Type", set);
		set = new HashSet<>();
		set.add("3.30GHz");
		refinments.put("Processor Speed", set);
		return refinments;
	}
}
