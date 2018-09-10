package com.synerzip.expenseCalculation.service;


import java.util.HashMap;
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
    expense.setUser(sessionUser.getUser());
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

  public void deleteExpense(int id) {
    expenseRepository.deleteById(id);
  }

  @SuppressWarnings("deprecation")
  public HashMap<String, Float> getMonthlyExpenses() {
    int userId = sessionUser.getUser().getId();
    List<Expense> expenseList = expenseRepository.findByUserId(userId);
    HashMap<String, Float> map = new HashMap<>();
    float monthlyExpense = 0;
    int month = 0;
    float amount = 0;

    for (int i = 1; i <= 12; i++) {

      monthlyExpense = 0;

      for (int j = 0; j < expenseList.size(); j++) {

        month = expenseList.get(j).getDate().getMonth();

        if ((month + 1) == (i)) {
          amount = expenseList.get(j).getAmount();
          monthlyExpense = monthlyExpense + amount;
        }
      }
      switch (i) {
        case 1:
          map.put("January", monthlyExpense);
          break;

        case 2:
          map.put("February", monthlyExpense);
          break;

        case 3:
          map.put("March", monthlyExpense);
          break;

        case 4:
          map.put("April", monthlyExpense);
          break;

        case 5:
          map.put("May", monthlyExpense);
          break;

        case 6:
          map.put("June", monthlyExpense);
          break;

        case 7:
          map.put("July", monthlyExpense);
          break;

        case 8:
          map.put("August", monthlyExpense);
          break;

        case 9:
          map.put("September", monthlyExpense);
          break;

        case 10:
          map.put("October", monthlyExpense);
          break;

        case 11:
          map.put("November", monthlyExpense);
          break;

        case 12:
          map.put("December", monthlyExpense);
          break;

      }
    }
    return map;
  }
}
