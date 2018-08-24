package com.synerzip.expenseCalculation.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.synerzip.expenseCalculation.exceptions.CategoryNameNotFoundException;
import com.synerzip.expenseCalculation.exceptions.CategoryNotFoundException;
import com.synerzip.expenseCalculation.model.Category;
import com.synerzip.expenseCalculation.model.Expense;
import com.synerzip.expenseCalculation.model.SessionUser;
import com.synerzip.expenseCalculation.repository.CategoryRepository;
import com.synerzip.expenseCalculation.repository.ExpenseRepository;

@Service
public class ExpenseService {

  @Autowired
  private CategoryService categoryService;

  @Autowired
  private ExpenseRepository expenseRepository;

  @Autowired
  private CategoryRepository categoryRepository;

  @Autowired
  SessionUser sessionUser;

  public Expense addExpense(Expense expense) throws CategoryNameNotFoundException {

    expense.setUserId(sessionUser.getUser().getId());
    Category category = null;
    try {
      category = categoryService.findByCategoryName(expense.getCategoryName());

    } catch (CategoryNotFoundException e) {

      Category newCategory = new Category();
      newCategory.setCategoryName(expense.getCategoryName());
      category = categoryRepository.save(newCategory);

    }
    expense.setCategory(category);
    return expenseRepository.save(expense);
  }

  public List<Expense> getAll() {
    int userid = sessionUser.getUser().getId();
    List<Expense> allExpenses = expenseRepository.findByUserId(userid);
    System.out.println(allExpenses);
    return allExpenses;

  }
}
