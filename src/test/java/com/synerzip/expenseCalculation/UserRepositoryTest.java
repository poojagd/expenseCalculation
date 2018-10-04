package com.synerzip.expenseCalculation;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;
import com.synerzip.expenseCalculation.model.User;
import com.synerzip.expenseCalculation.repository.UserRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase
public class UserRepositoryTest {

  @Autowired
  private TestEntityManager entityManager;

  @Autowired
  private UserRepository userRepository;

  @Test
  public void testFindByUserId() {

    User user = new User("demo", "demo", "demo@gmail.com", "demo");

    entityManager.persistAndFlush(user);

    User foundUser = userRepository.findByUserId(user.getId());

    assertThat(foundUser.getId()).isEqualTo(user.getId());
    assertEquals(user, foundUser);
  }

  @Test
  public void testFindByEmail() {

    User user = new User("demo", "demo", "demo@gmail.com", "demo");

    entityManager.persistAndFlush(user);

    User foundUser = userRepository.findByUserId(user.getId());

    assertThat(foundUser.getEmail()).isEqualTo(user.getEmail());
  }
}
