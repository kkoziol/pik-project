package com.project.pik.EbayView.services;

import java.util.List;

import com.project.pik.EbayView.daos.UserDao;
import com.project.pik.EbayView.models.entities.User;

public interface UserService {
	public boolean saveOrUpdate(User user);
	public User getUser(int id);
	public List<User> getUserList();
	public void deleteUser(int id);
	public UserDao getUserDao();
}
