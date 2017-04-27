package com.model;

import java.sql.Date;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
	@Column(name = "NAME", nullable = true, length = 40)
	private String name;
	@Column(name = "SURNAME", nullable = true, length = 40)
	private String surname;
	@Column(name = "EMAIL", nullable = true, length = 50)
	private String email;
	@Column(name = "LOGIN", nullable = true, length = 20)
	private String login;
	@Column(name = "PASSWORD", nullable = true, length = 20)
	private String password;
	@Column(name = "DATE_OF_BIRTH", nullable = true)
	private Date dateOfBirth = new java.sql.Date(Calendar.getInstance().getTime().getTime());
	@Column(name = "DATE_OF_REGISTRATION", nullable = true)
	private Date dateOfRegistration = new java.sql.Date(Calendar.getInstance().getTime().getTime());
	@Column(name = "SEX", nullable = true, length = 20)
	private String sex;
	@Column(name = "AUTHORITIES", nullable = true, length = 20)
	private String authorities = "ROLE_USER";

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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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
		return "User [user_id=" + userId + ", name=" + name + ", surname=" + surname + ", email=" + email + ", login="
				+ login + ", password=" + password + ", dateOfBirth=" + dateOfBirth + ", dateOfRegistration="
				+ dateOfRegistration + ", sex=" + sex + ", authorities=" + authorities + "]";
	}

	
	
}
