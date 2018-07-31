package com.synerzip.expenseCalculation;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.synerzip.expenseCalculation.controller.UserController;
import com.synerzip.expenseCalculation.model.User;

import static org.junit.Assert.assertEquals;;

public class UserControllerTest {
	
	@Autowired
	User user;
	
	@Autowired
	UserController c;
	
	@Test
	public void testCreateUser() {
		assertEquals(user, c.create(user));
		
	}

}
