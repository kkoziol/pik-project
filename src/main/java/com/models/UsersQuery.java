package com.models;

public class UsersQuery {
	private String query;

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}
	
	@Override
	public String toString(){
		return "usersquery: " + query;
	}
}
