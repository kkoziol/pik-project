package com.project.pik.EbayApi.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;



@Entity
@Table(name="Orders")
public class Order {
	@Id
	@Column(name = "ORDER_ID", nullable = false, unique = true, length = 11)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer orderId;
	
	@JsonManagedReference
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "USER_ID", nullable = false)
	private User user;
	
	@Column(name = "IS_HIRTORY_LOG", nullable = false)
	private boolean isHistoryLog;
	
	@JsonBackReference
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "order",cascade=CascadeType.REMOVE)
	private Set<FoundResult> foundResults = new HashSet<>();
	
	@Column(name = "PREFERENCES",length=2000)
	private String preferencesAsJson;

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public boolean isHistoryLog() {
		return isHistoryLog;
	}

	public void setHistoryLog(boolean isHistoryLog) {
		this.isHistoryLog = isHistoryLog;
	}


	public Set<FoundResult> getFoundResults() {
		return foundResults;
	}

	public void setFoundResults(Set<FoundResult> foundResults) {
		this.foundResults = foundResults;
	}

	public String getPreferencesAsJson() {
		return preferencesAsJson;
	}

	public void setPreferencesAsJson(String preferencesAsJson) {
		this.preferencesAsJson = preferencesAsJson;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((foundResults == null) ? 0 : foundResults.hashCode());
		result = prime * result + (isHistoryLog ? 1231 : 1237);
		result = prime * result + ((orderId == null) ? 0 : orderId.hashCode());
		result = prime * result + ((preferencesAsJson == null) ? 0 : preferencesAsJson.hashCode());
		result = prime * result + ((user == null) ? 0 : user.hashCode());
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
		Order other = (Order) obj;
		if (foundResults == null) {
			if (other.foundResults != null)
				return false;
		} else if (!foundResults.equals(other.foundResults))
			return false;
		if (isHistoryLog != other.isHistoryLog)
			return false;
		if (orderId == null) {
			if (other.orderId != null)
				return false;
		} else if (!orderId.equals(other.orderId))
			return false;
		if (preferencesAsJson == null) {
			if (other.preferencesAsJson != null)
				return false;
		} else if (!preferencesAsJson.equals(other.preferencesAsJson))
			return false;
		if (user == null) {
			if (other.user != null)
				return false;
		} else if (!user.equals(other.user))
			return false;
		return true;
	}
	
	
}