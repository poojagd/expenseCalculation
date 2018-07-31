package com.synerzip.expenseCalculation.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.synerzip.expenseCalculation.model.Category;
import com.synerzip.expenseCalculation.model.Expense;
import com.synerzip.expenseCalculation.model.User;
import com.synerzip.expenseCalculation.repository.CategoryRepository;
import com.synerzip.expenseCalculation.repository.ExpenseRepository;
import com.synerzip.expenseCalculation.repository.UserRepository;

@Service
public class ExpenseService {

	@Autowired
	UserRepository userRepository;

	@Autowired
	CategoryRepository categoryRepository;

	@Autowired
	ExpenseRepository expenseRepository;

	public Expense addExpense(int userId, Expense expense) {
		User user = userRepository.findByUserId(userId);
		expense.setUser(user);
		Category category = categoryRepository.findByCategoryName(expense.getCategory().getCategoryName());
		expense.setCategory(category);
		return expenseRepository.save(expense);
	}

}
