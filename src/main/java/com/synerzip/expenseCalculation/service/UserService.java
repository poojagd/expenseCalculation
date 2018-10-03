package com.synerzip.expenseCalculation.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.synerzip.expenseCalculation.exceptions.EmailIdExistsException;
import com.synerzip.expenseCalculation.model.SessionUser;
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
  SessionUser sessionUser;

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

  public User updatePassword(String password) {

    String email = sessionUser.getUser().getEmail();
    User user = userRepository.findByEmail(email);

    String encodedPassword = passwordEncoder().encode(password);
    user.setPassword(encodedPassword);
    userRepository.save(user);
    return user;
  }

  public User updateFirstNameAndLastName(User user) {

    String email = sessionUser.getUser().getEmail();
    User foundUser = userRepository.findByEmail(email);
    if (!user.getFirstName().isEmpty()) {
      String firstName = user.getFirstName();
      foundUser.setFirstName(firstName);
    }

    if (!user.getLastName().isEmpty()) {
      String lastName = user.getLastName();
      foundUser.setLastName(lastName);
    }
    userRepository.save(foundUser);
    return foundUser;
  }

}
