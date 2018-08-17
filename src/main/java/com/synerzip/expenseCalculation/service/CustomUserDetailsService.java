package com.synerzip.expenseCalculation.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.synerzip.expenseCalculation.repository.UserRepository;
import static java.util.Collections.emptyList;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String emailId) throws UsernameNotFoundException {

		com.synerzip.expenseCalculation.model.User user = userRepository.findByEmail(emailId);

		if (user == null) {
			throw new UsernameNotFoundException("EmailId not found.");
		}
		return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), emptyList());
	}

}
