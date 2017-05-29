package com.project.pik.EbayView.models.entities;

import java.sql.Date;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity
@Table(name = "Users")
public class User {
	@Id
	@Column(name = "USER_ID", nullable = false, unique = true, length = 11)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer userId;
	@Column(name = "NAME", nullable = true, length = 35)
	private String name;
	@Column(name = "SURNAME", nullable = true, length = 35)
	private String surname;
	@Column(name = "LOGIN", nullable = true, length = 20)
	private String login;
	@Column(name = "PASSWORD", nullable = true, length = 60)
	private String password;
	@Column(name = "DATE_OF_BIRTH", nullable = true)
	private Date dateOfBirth = new java.sql.Date(Calendar.getInstance().getTime().getTime());
	@Column(name = "DATE_OF_REGISTRATION", nullable = true)
	private Date dateOfRegistration = new java.sql.Date(Calendar.getInstance().getTime().getTime());
	@Column(name = "SEX", nullable = true, length = 20)
	private String sex;
	@Column(name = "AUTHORITIES", nullable = true, length = 20)
	private String authorities = "ROLE_USER";
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
	private Set<Email> emails = new HashSet<>();
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
	private Set<Order> orders = new HashSet<>();
	
	
	public Set<Email> getEmails() {
		return emails;
	}

	public void setEmails(Set<Email> emails) {
		this.emails = emails;
	}

	public Set<Order> getOrders() {
		return orders;
	}

	public void setOrders(Set<Order> orders) {
		this.orders = orders;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	@Fetch(FetchMode.SELECT)
	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Date getDateOfRegistration() {
		return dateOfRegistration;
	}

	public void setDateOfRegistration(Date dateOfRegistration) {
		this.dateOfRegistration = dateOfRegistration;
	}

	public String getAuthorities() {
		return authorities;
	}

	public void setAuthorities(String authorities) {
		this.authorities = authorities;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	@Override
	public String toString() {
		return "User [user_id=" + userId + ", name=" + name + ", surname=" + surname + ", login="
				+ login + ", password=" + password + ", dateOfBirth=" + dateOfBirth + ", dateOfRegistration="
				+ dateOfRegistration + ", sex=" + sex + ", authorities=" + authorities + "]";
	}
}