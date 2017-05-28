package com.project.pik.EbayApi.daos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.pik.EbayApi.model.User;

public interface UserRepository extends JpaRepository<User,Long>{
	
}
