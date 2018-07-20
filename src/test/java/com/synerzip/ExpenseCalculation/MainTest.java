package com.synerzip.ExpenseCalculation;

import org.junit.Test;

import com.synerzip.expenseCalculation.controller.UserController;
import com.synerzip.expenseCalculation.model.User;

import static org.junit.Assert.assertEquals;;

public class MainTest {
	User user = new User();
	UserController c = new UserController();
	
	@Test
	public void testRegistration() {
		assertEquals(user, c.create(user));
	}

}
