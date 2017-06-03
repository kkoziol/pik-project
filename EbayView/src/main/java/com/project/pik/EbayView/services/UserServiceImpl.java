package com.project.pik.EbayView.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.pik.EbayView.models.UserRepository;
import com.project.pik.EbayView.models.entities.User;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository userRepo;

	@Override
	public boolean registerUser(com.project.pik.EbayView.jsonEntities.User user) {

		List<User> users = userRepo.findByLogin(user.getUsername());
		System.out.println("users size: " + users.size());

		if (users.isEmpty() && (user.getPassword().equals(user.getConfirmPassword()))) {
			User userToAdd = new User();
			userToAdd.setAuthorities("ROLE_USER");
			// userToAdd.setDateOfBirth(user.getBirthDate()); TO_DO
			userToAdd.setLogin(user.getUsername());
			userToAdd.setName(user.getFirstName());
			userToAdd.setPassword(user.getPassword());
			userToAdd.setSex(user.getGender());
			userToAdd.setSurname(user.getLastName());
			// userToAdd.setUserId(3);
			userRepo.save(userToAdd);
			System.out.println("USER ADDED");
			return true;
		} else
			return false;
	}

}
