package com.synerzip.expenseCalculation;

import static org.junit.Assert.*;
import org.junit.Test;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCrypt;
import com.synerzip.expenseCalculation.model.CustomUserDetails;
import com.synerzip.expenseCalculation.model.User;

public class LoginTest {

  @Bean
  public User setUser() {
    return new User("demo", "demo", "demo@synerzip.com", "demo");
  }

  User user = setUser();

  @Bean
  public CustomUserDetails setCustomUserDetails() {
    return new CustomUserDetails(user);
  }

  CustomUserDetails userDetails = setCustomUserDetails();

  @Test
  public void testUsernameAndPassword() {
    user.setPassword(BCrypt.hashpw("demo", BCrypt.gensalt(10)));
    assertNotNull(userDetails.getEmail());
    assertNotNull(userDetails.getPassword());
  }

  @Test
  public void testLoadUserByUsername() {
    assertTrue(BCrypt.checkpw(user.getPassword(),
        BCrypt.hashpw(userDetails.getPassword(), BCrypt.gensalt(10))));
    assertEquals(user.getEmail(), userDetails.getUsername());
  }

}
