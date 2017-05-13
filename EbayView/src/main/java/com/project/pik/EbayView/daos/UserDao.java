package com.project.pik.EbayView.daos;

import java.util.List;

import com.project.pik.EbayView.models.entities.User;

public interface UserDao {
	public boolean saveOrUpdate(User user);
	public User getUser(int id);
	public List<User> getUserList();
	public void deleteUser(int id);
}
