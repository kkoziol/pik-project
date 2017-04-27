package com.services;

import java.util.List;

import com.daos.UserDao;

import com.model.User;

public interface UserService {
	public boolean saveOrUpdate(User user);
	public User getUser(int id);
	public List<User> getUserList();
	public void deleteUser(int id);
	public UserDao getUserDao();
}
