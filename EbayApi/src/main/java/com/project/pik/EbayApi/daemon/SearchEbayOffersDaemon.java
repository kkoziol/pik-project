package com.project.pik.EbayApi.daemon;

import static com.project.pik.EbayApi.consts.ApiConsts.INITIAL_DELAY_SEC;
import static com.project.pik.EbayApi.consts.ApiConsts.TIME_INTERVAL_SEC;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class SearchEbayOffersDaemon {
	private static SearchEbayOffersDaemon instance = null;
	
	public static void runInstance(){
		if(instance == null){
			instance = new SearchEbayOffersDaemon();
		}
	}
	
	private SearchEbayOffersDaemon(){
		ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
		executor.scheduleAtFixedRate(new SearchThread(), INITIAL_DELAY_SEC, TIME_INTERVAL_SEC, TimeUnit.SECONDS);
	}
}
