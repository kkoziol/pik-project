package com.daos;

import java.util.List;

import com.models.entities.User;

public interface UserDao {
	public boolean saveOrUpdate(User user);
	public User getUser(int id);
	public List<User> getUserList();
	public void deleteUser(int id);
}
