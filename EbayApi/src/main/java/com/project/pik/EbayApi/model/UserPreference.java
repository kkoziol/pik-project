package com.project.pik.EbayApi.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;


public class UserPreference {
	private static final Logger logger = Logger.getLogger(UserPreference.class);
	private static Map<String, String> conditionMnemonicToCodeMap = new HashMap<>();
	static{
		// https://developer.ebay.com/devzone/finding/callref/types/ItemFilterType.html
		// section: Condition
		conditionMnemonicToCodeMap.put("New", "1000");
		conditionMnemonicToCodeMap.put("New other (see details)", "1500");
		conditionMnemonicToCodeMap.put("New with defects", "1750");
		conditionMnemonicToCodeMap.put("Manufacturer refurbished", "2000");
		conditionMnemonicToCodeMap.put("Seller refurbished", "2500");
		conditionMnemonicToCodeMap.put("Used", "3000");
		conditionMnemonicToCodeMap.put("Very Good", "4000");
		conditionMnemonicToCodeMap.put("Good", "5000");
		conditionMnemonicToCodeMap.put("Acceptable", "6000");
		conditionMnemonicToCodeMap.put("For parts or not working", "7000");
	}
	private Map<String, Set<String>> categorySpecifics;
	private String categoryId;
	private String priceMax;
	private String priceMin;
	private List<String> conditions;
	private String deliveryOptions;
	private String keyword;
	
	
	
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public Map<String, Set<String>> getCategorySpecifics() {
		return categorySpecifics;
	}
	public void setCategorySpecifics(Map<String, Set<String>> categorySpecifics) {
		this.categorySpecifics = categorySpecifics;
	}
	public String getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}
	public String getPriceMax() {
		return priceMax;
	}
	public void setPriceMax(String prizeMax) {
		this.priceMax = prizeMax;
	}
	public String getPriceMin() {
		return priceMin;
	}
	public void setPriceMin(String prizeMin) {
		this.priceMin = prizeMin;
	}
	
	public List<String> getConditions() {
		return conditions;
	}
	public void setConditions(List<String> condition) {
		this.conditions = condition;
	}
	public String getDeliveryOptions() {
		return deliveryOptions;
	}
	public void setDeliveryOptions(String deliveryOptions) {
		this.deliveryOptions = deliveryOptions;
	}
	public static String mapMnemonicToCode(String mnemonic) {
		if(conditionMnemonicToCodeMap.containsKey(mnemonic)){
			return conditionMnemonicToCodeMap.get(mnemonic);
		} else{
			logger.error("Mnemonic: '" + mnemonic + "' couldn't be mapped to code");
			return "";
		}
	}
}
