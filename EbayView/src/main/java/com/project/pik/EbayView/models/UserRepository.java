package com.project.pik.EbayView.models;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.pik.EbayView.models.entities.User;

public interface UserRepository extends JpaRepository<User, Long>{
	
	List<User> findByLogin(String login);

}
