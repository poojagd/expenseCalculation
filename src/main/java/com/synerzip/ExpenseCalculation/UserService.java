package com.synerzip.ExpenseCalculation;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;


@Service
public class UserService {
	
	@PersistenceContext
	EntityManager entityManager;

	@Autowired
	private UserRepository userRepository;
	
	public User addUser(User user) {
		String p=user.getPassword();
		user.setPassword(BCrypt.hashpw(user.getPassword(),BCrypt.gensalt(10))); 
		return userRepository.save(user);
	}
	
	
		
	}
