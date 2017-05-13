package com.project.pik.EbayView.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.pik.EbayView.daos.UserDao;
import com.project.pik.EbayView.models.entities.User;

@Service
public class UserServiceImpl implements UserService {
	private UserDao userDao;

	@Override
	public UserDao getUserDao() {
		return userDao;
	}

	@Autowired
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	@Override
	public boolean saveOrUpdate(User user) {
		return userDao.saveOrUpdate(user);
	}

	@Override
	public User getUser(int id) {
		return userDao.getUser(id);
	}

	@Override
	public void deleteUser(int id) {
		userDao.deleteUser(id);
	}

	@Override
	public List<User> getUserList() {
		return userDao.getUserList();
	}
}
