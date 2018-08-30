package com.synerzip.expenseCalculation.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.synerzip.expenseCalculation.exceptions.EmailIdExistsException;
import com.synerzip.expenseCalculation.model.User;
import com.synerzip.expenseCalculation.repository.UserRepository;

@Service
public class UserService {

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new PasswordEncoder() {

      @Override
      public String encode(CharSequence rawPassword) {
        return BCrypt.hashpw((String) rawPassword, BCrypt.gensalt(10));

      }

      @Override
      public boolean matches(CharSequence rawPassword, String encodedPassword) {
        String str = rawPassword.toString();
        return BCrypt.checkpw(str, encodedPassword);

      }

    };
  }

  @Autowired
  private UserRepository userRepository;

  public User create(User user) throws EmailIdExistsException {
    String rawPassword = user.getPassword();
    String encodedPassword = passwordEncoder().encode(rawPassword);

    user.setPassword(encodedPassword);
    
    User founduser = userRepository.findByEmail(user.getEmail());
    if (founduser != null) {
      throw new EmailIdExistsException("EmailId already exists. Please enter new emailId.");
    }
    return userRepository.save(user);
  }

  public User findByEmail(String email) {

    User user = userRepository.findByEmail(email);
    return user;

  }
}
