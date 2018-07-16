package com.synerzip.ExpenseCalculation;

import org.junit.Test;
import static org.junit.Assert.assertEquals;;

public class MainTest {
	User user = new User();
	ControllerClass c = new ControllerClass();
	LoginUser l = new LoginUser();

	@Test
	public void testRegistration() {
		assertEquals(user, c.registerUser(user));
	}

//	@Test
//	public void testLogin() {
//		assertEquals("/dashboard", c.loginUser(l));
//	}

}
