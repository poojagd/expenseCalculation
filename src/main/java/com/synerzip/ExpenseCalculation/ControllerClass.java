package com.synerzip.ExpenseCalculation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@ControllerAdvice
@RequestMapping(path = "/app")
public class ControllerClass {

	@Autowired
	UserService userService;

	@PostMapping(path = "/register")
	public User registerUser(@RequestBody User user) {
		return userService.addUser(user);
	}
	

}
