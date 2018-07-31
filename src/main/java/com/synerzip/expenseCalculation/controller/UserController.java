package com.synerzip.expenseCalculation.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.synerzip.expenseCalculation.model.User;
import com.synerzip.expenseCalculation.service.UserService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(path = "/users")
public class UserController {

	@Autowired
	UserService userService;

	@CrossOrigin(origins = "*")
	@PostMapping
	public User create(@Valid @RequestBody User user) {
		return userService.create(user);
	}

}
