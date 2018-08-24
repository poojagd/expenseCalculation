package com.synerzip.expenseCalculation;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.init.ScriptException;
import com.synerzip.expenseCalculation.controller.UserController;
import com.synerzip.expenseCalculation.exceptions.EmailIdExistsException;
import com.synerzip.expenseCalculation.model.User;
import static org.junit.Assert.assertEquals;
import java.sql.SQLException;;

public class UserControllerTest {

  @Autowired
  User user;

  @Autowired
  UserController c;

  @Test
  public void testCreateUser() throws SQLException, EmailIdExistsException {

    assertEquals(user, c.create(user));

  }

}
