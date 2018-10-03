package com.synerzip.expenseCalculation;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.junit4.SpringRunner;
import com.synerzip.expenseCalculation.model.User;
import com.synerzip.expenseCalculation.repository.UserRepository;
import com.synerzip.expenseCalculation.service.CustomUserDetailsService;

@RunWith(SpringRunner.class)
public class CustomUserDetailsServiceTest {

  @TestConfiguration
  static class CustomUserDetailsServiceTestContextConfiguration {

    @Bean
    public CustomUserDetailsService userDetailsService() {
      return new CustomUserDetailsService();
    }
  }

  @Autowired
  CustomUserDetailsService userDetailsService;

  @MockBean
  UserRepository userRepository;

  @Test
  public void testLoadUserByUsername() {
    User user = new User(1, "demo", "demo", "demo@gmail.com", "demo");

    String email = "demo@gmail.com";

    Mockito.when(userRepository.findByEmail(email)).thenReturn(user);

    UserDetails foundUser = (UserDetails) userDetailsService.loadUserByUsername(email);
    assertEquals(user.getEmail(), foundUser.getUsername());
    assertEquals(user.getPassword(), foundUser.getPassword());

  }

  @Test(expected = UsernameNotFoundException.class)
  public void testLoadUserByUsername_throwsUsernameNotFoundException() {

    String email = "demo@gmail.com";
    userDetailsService.loadUserByUsername(email);

  }

}
