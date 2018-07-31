package com.synerzip.expenseCalculation.service;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.synerzip.expenseCalculation.model.CustomUserDetails;
import com.synerzip.expenseCalculation.model.User;
import com.synerzip.expenseCalculation.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String emailId) throws UsernameNotFoundException {

		Optional<User> user = userRepository.findByemailId(emailId);

		user.orElseThrow(() -> new UsernameNotFoundException("EmailId not found."));

		CustomUserDetails userDetails = user.map((users) -> {
			return new CustomUserDetails(users);
		}).get();

		return userDetails;
	}

}
