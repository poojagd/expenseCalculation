package com.synerzip.expenseCalculation.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.synerzip.expenseCalculation.exceptions.CategoryNameNotFoundException;
import com.synerzip.expenseCalculation.exceptions.CategoryNotFoundException;
import com.synerzip.expenseCalculation.model.Category;
import com.synerzip.expenseCalculation.model.Expense;
import com.synerzip.expenseCalculation.model.SessionUser;
import com.synerzip.expenseCalculation.repository.ExpenseRepository;

@Service
public class ExpenseService {

  @Autowired
  private CategoryService categoryService;

  @Autowired
  private ExpenseRepository expenseRepository;

  @Autowired
  SessionUser sessionUser;

  public Expense addExpense(Expense expense) throws CategoryNameNotFoundException {
    int id = sessionUser.getUser().getId();
    expense.setUserId(id);
    Category category = null;
    try {
      category = categoryService.findByCategoryName(expense.getCategoryName());

    } catch (CategoryNotFoundException e) {

      Category newCategory = new Category();
      newCategory.setCategoryName(expense.getCategoryName());
      category = categoryService.create(newCategory);

    }
    expense.setCategory(category);
    return expenseRepository.save(expense);
  }

  public List<Expense> getAll() {
    int userId = sessionUser.getUser().getId();
    List<Expense> allExpenses = expenseRepository.findByUserId(userId);
    return allExpenses;

  }
}
