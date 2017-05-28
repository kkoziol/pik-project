package com.project.pik.EbayView.models.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Table(name="FOUND_RESULTS")
public class FoundResult {
	@Id
	@Column(name = "FOUND_RESULT_ID", nullable = false, unique = true, length = 11)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer foundResultId;
	
	@Column(name = "URL", nullable = false, length = 2000)
	private String url;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ORDER_ID", nullable = false)
	private Order order;

	public Integer getFoundResultId() {
		return foundResultId;
	}

	public void setFoundResultId(Integer foundResultId) {
		this.foundResultId = foundResultId;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}
