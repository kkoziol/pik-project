package com.project.pik.EbayApi.model;

import java.sql.Date;
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
	private Date dateAndTime;
	
	
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
	
	public Date getDateAndTime() {
		return dateAndTime;
	}
	public void setDateAndTime(Date dateAndTime) {
		this.dateAndTime = dateAndTime;
	}
	public static String mapMnemonicToCode(String mnemonic) {
		if(conditionMnemonicToCodeMap.containsKey(mnemonic)){
			return conditionMnemonicToCodeMap.get(mnemonic);
		} else{
			logger.error("Mnemonic: '" + mnemonic + "' couldn't be mapped to code");
			return "";
		}
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((categoryId == null) ? 0 : categoryId.hashCode());
		result = prime * result + ((categorySpecifics == null) ? 0 : categorySpecifics.hashCode());
		result = prime * result + ((conditions == null) ? 0 : conditions.hashCode());
		result = prime * result + ((dateAndTime == null) ? 0 : dateAndTime.hashCode());
		result = prime * result + ((deliveryOptions == null) ? 0 : deliveryOptions.hashCode());
		result = prime * result + ((keyword == null) ? 0 : keyword.hashCode());
		result = prime * result + ((priceMax == null) ? 0 : priceMax.hashCode());
		result = prime * result + ((priceMin == null) ? 0 : priceMin.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserPreference other = (UserPreference) obj;
		if (categoryId == null) {
			if (other.categoryId != null)
				return false;
		} else if (!categoryId.equals(other.categoryId))
			return false;
		if (categorySpecifics == null) {
			if (other.categorySpecifics != null)
				return false;
		} else if (!categorySpecifics.equals(other.categorySpecifics))
			return false;
		if (conditions == null) {
			if (other.conditions != null)
				return false;
		} else if (!conditions.equals(other.conditions))
			return false;
		if (dateAndTime == null) {
			if (other.dateAndTime != null)
				return false;
		} else if (!dateAndTime.equals(other.dateAndTime))
			return false;
		if (deliveryOptions == null) {
			if (other.deliveryOptions != null)
				return false;
		} else if (!deliveryOptions.equals(other.deliveryOptions))
			return false;
		if (keyword == null) {
			if (other.keyword != null)
				return false;
		} else if (!keyword.equals(other.keyword))
			return false;
		if (priceMax == null) {
			if (other.priceMax != null)
				return false;
		} else if (!priceMax.equals(other.priceMax))
			return false;
		if (priceMin == null) {
			if (other.priceMin != null)
				return false;
		} else if (!priceMin.equals(other.priceMin))
			return false;
		return true;
	}
	
	
}
