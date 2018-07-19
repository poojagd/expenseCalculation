package com.synerzip.expenseCalculation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.synerzip.expenseCalculation.model.User;
import com.synerzip.expenseCalculation.service.UserService;

@RestController
@RequestMapping(path = "/user")
public class UserController {

	@Autowired
	UserService userService;

	@PostMapping(path = "/register")
	public User registerUser(@RequestBody User user) {
		return userService.addUser(user);
	}
}
