package com.project.pik.EbayApi.daemon;

import java.util.Map;
import java.util.Set;

public class UserPreference {
	private Map<String, Set<String>> refinmentsAsSet;
	//private Map<String, Pair<String, String>> entriesAsRange;
	private String categoryId;
	private String prizeMax;
	private String prizeMin;
	private String condition;
	private String deliveryOptions;
	public Map<String, Set<String>> getRefinmentsAsSet() {
		return refinmentsAsSet;
	}
	public void setRefinmentsAsSet(Map<String, Set<String>> refinmentsAsSet) {
		this.refinmentsAsSet = refinmentsAsSet;
	}
	public String getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}
	public String getPrizeMax() {
		return prizeMax;
	}
	public void setPrizeMax(String prizeMax) {
		this.prizeMax = prizeMax;
	}
	public String getPrizeMin() {
		return prizeMin;
	}
	public void setPrizeMin(String prizeMin) {
		this.prizeMin = prizeMin;
	}
	public String getCondition() {
		return condition;
	}
	public void setCondition(String condition) {
		this.condition = condition;
	}
	public String getDeliveryOptions() {
		return deliveryOptions;
	}
	public void setDeliveryOptions(String deliveryOptions) {
		this.deliveryOptions = deliveryOptions;
	}
	
	
}
