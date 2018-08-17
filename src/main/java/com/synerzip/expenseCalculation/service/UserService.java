package com.synerzip.expenseCalculation.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import com.synerzip.expenseCalculation.model.User;
import com.synerzip.expenseCalculation.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	public User create(User user) {
		user.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt(10)));
		return userRepository.save(user);
	}
	
	public User findByEmail(String email) {
		User user = userRepository.findByEmail(email);
		return user;
	}
}
