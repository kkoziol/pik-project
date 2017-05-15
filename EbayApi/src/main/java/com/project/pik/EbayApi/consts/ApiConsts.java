package com.project.pik.EbayApi.consts;

import com.ebay.soap.eBLBaseComponents.SiteCodeType;

public class ApiConsts {
	private ApiConsts() {
	}
	
	public static final int TIME_INTERVAL_SEC = 1;
	public static final int INITIAL_DELAY_SEC = 5; 
	public static final String PROPERTIES_FILE_NAME = "/ebay.properties";
	public static final String SEARCHING_CURRENCY = "EUR";
	public static final SiteCodeType SITE_CODING = SiteCodeType.US;
}
