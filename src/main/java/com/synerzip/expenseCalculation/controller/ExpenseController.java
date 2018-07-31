package com.synerzip.expenseCalculation.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.synerzip.expenseCalculation.model.Expense;
import com.synerzip.expenseCalculation.service.ExpenseService;

@RestController
@RequestMapping(path = "/expenses")
public class ExpenseController {

	@Autowired
	ExpenseService expenseService;

	@PostMapping(path = "/{userId}")
	public Expense addExpense(@PathVariable int userId, @Valid @RequestBody Expense expense) {

		return expenseService.addExpense(userId, expense);

	}
}
