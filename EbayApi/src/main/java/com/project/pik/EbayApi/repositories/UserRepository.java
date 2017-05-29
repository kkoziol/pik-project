package com.project.pik.EbayApi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.pik.EbayApi.model.User;

@Repository
public interface UserRepository extends JpaRepository<User,Long>{
	
}
