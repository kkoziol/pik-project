package com.project.pik.EbayApi.model;

import java.util.HashMap;
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
	private String prizeMax;
	private String prizeMin;
	private String condition;
	private String deliveryOptions;
	
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
	public static String mapMnemonicToCode(String mnemonic) {
		if(conditionMnemonicToCodeMap.containsKey(mnemonic)){
			return conditionMnemonicToCodeMap.get(mnemonic);
		} else{
			logger.error("Mnemonic: '" + mnemonic + "' couldn't be mapped to code");
			return "";
		}
	}
}
