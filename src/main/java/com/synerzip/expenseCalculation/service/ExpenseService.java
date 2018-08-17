package com.synerzip.expenseCalculation.service;


import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import com.synerzip.expenseCalculation.model.Category;
import com.synerzip.expenseCalculation.model.Expense;
import com.synerzip.expenseCalculation.model.User;
import com.synerzip.expenseCalculation.repository.ExpenseRepository;

@Service
public class ExpenseService {

	@Autowired
	private UserService userService;

	@Autowired
	private CategoryService categoryService;

	@Autowired
	private ExpenseRepository expenseRepository;
	
	public Expense addExpense(Expense expense) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String email = auth.getName();
		User user = userService.findByEmail(email);
		expense.setUserId(user.getId());//set userid only
		Category category = categoryService.findByCategoryName(expense.getCategoryName());
		expense.setCategory(category);
		return expenseRepository.save(expense);
	}
	
	public List<Expense> getAll() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String email = auth.getName();
		User user = userService.findByEmail(email);
		List<Expense> allExpenses = expenseRepository.findAllByUserId(user.getId());
		return allExpenses;
		
	}
}
