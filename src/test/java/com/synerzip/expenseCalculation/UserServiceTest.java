package com.synerzip.expenseCalculation;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Answers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.synerzip.expenseCalculation.exceptions.EmailIdExistsException;
import com.synerzip.expenseCalculation.model.SessionUser;
import com.synerzip.expenseCalculation.model.User;
import org.springframework.test.context.junit4.SpringRunner;
import com.synerzip.expenseCalculation.repository.UserRepository;
import com.synerzip.expenseCalculation.service.UserService;

@RunWith(SpringRunner.class)
public class UserServiceTest {

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

  @TestConfiguration
  static class UsereServiceTestContextConfiguration {

    @Bean
    public UserService userService() {
      return new UserService();
    }

  }

  @Autowired
  UserService userService;

  @MockBean(answer = Answers.RETURNS_DEEP_STUBS)
  SessionUser sessionUser;

  @MockBean
  UserRepository userRepository;

  User user = new User(1, "demo", "demo", "demo@gmail.com", "demo");

  @Test
  public void testCreate() throws EmailIdExistsException {

    String rawPassword = user.getPassword();
    String encodedPassword = passwordEncoder().encode(rawPassword);
    user.setPassword(encodedPassword);

    Mockito.when(userRepository.save(user)).thenReturn(user);

    User createdUser = userService.create(user);
    assertEquals(user, createdUser);
  }

  @Test(expected = EmailIdExistsException.class)
  public void testCreate_throwsEmailIdExistsException() throws EmailIdExistsException {

    String rawPassword = user.getPassword();
    String encodedPassword = passwordEncoder().encode(rawPassword);
    user.setPassword(encodedPassword);

    Mockito.when(userRepository.findByEmail(user.getEmail())).thenReturn(user);

    User createdUser = userService.create(user);
    assertEquals(user, createdUser);
  }

  @Test
  public void testFindByEmail() {
    String email = "demo@gmail.com";
    Mockito.when(userRepository.findByEmail(email)).thenReturn(user);

    User foundUser = userService.findByEmail(email);
    assertEquals(user, foundUser);

  }

  @Test
  public void testUpdatePassword() {
    String email = "demo@gmail.com";
    Mockito.when(sessionUser.getUser().getEmail()).thenReturn(email);

    Mockito.when(userRepository.findByEmail(email)).thenReturn(user);

    String encodedPassword = passwordEncoder().encode("newPassword");
    user.setPassword(encodedPassword);

    Mockito.when(userRepository.save(user)).thenReturn(user);

    User returnedUser = userService.updatePassword("newPassword");

    assertEquals(user, returnedUser);

  }

  @Test
  public void testUpdateFirstNameAndLastName() {

    User userArgument = new User();

    userArgument.setFirstName("pooja");
    userArgument.setLastName("devray");

    String email = "demo@gmail.com";
    Mockito.when(sessionUser.getUser().getEmail()).thenReturn(email);

    Mockito.when(userRepository.findByEmail(email)).thenReturn(user);

    user.setFirstName(userArgument.getFirstName());
    user.setLastName(userArgument.getLastName());

    Mockito.when(userRepository.save(user)).thenReturn(user);
    User returnedUser = userService.updateFirstNameAndLastName(userArgument);

    assertEquals(user, returnedUser);

  }

}
