package com.synerzip.expenseCalculation.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
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
  public Map<String, Object> getMonthlyExpenses() {
    int userId = sessionUser.getUser().getId();
    List<Expense> expenseList = expenseRepository.findByUserId(userId);
    Map<String, Object> map = new HashMap<>();
    float monthlyExpense = 0;
    int month = 0;
    float amount = 0;
    // looping over 12 months
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

  public Map<String, Object> monthlyReport(String month) {

    int userId = sessionUser.getUser().getId();

    List<Expense> expenseList = expenseRepository.findByUserId(userId);

    List<Expense> monthlyExpenseList = new ArrayList<>();

    Set<Object> categoryNames = new HashSet<>();

    Map<String, Object> amountForCategoryName = new HashMap<>();

    float monthlyExpense = 0;

    int id = 0, monthId = 0;

    float amount = 0;

    switch (month) {
      case "JAN":
        monthId = 1;
        break;

      case "FEB":
        monthId = 2;
        break;

      case "MAR":
        monthId = 3;
        break;

      case "APR":
        monthId = 4;
        break;

      case "MAY":
        monthId = 5;
        break;

      case "JUN":
        monthId = 6;
        break;

      case "JUL":
        monthId = 7;
        break;

      case "AUG":
        monthId = 8;
        break;

      case "SEP":
        monthId = 9;
        break;

      case "OCT":
        monthId = 10;
        break;

      case "NOV":
        monthId = 11;
        break;

      case "DEC":
        monthId = 12;
        break;

    }

    // calculating total expenditure of the specific month
    for (int j = 0; j < expenseList.size(); j++) {

      id = expenseList.get(j).getDate().getMonth();

      if ((id + 1) == (monthId)) {
        // creating list of categories of the specific month
        categoryNames.add(expenseList.get(j).getCategory().getCategoryName());

        // storing list of expenses of specific month in new list
        monthlyExpenseList.add(expenseList.get(j));

        amount = expenseList.get(j).getAmount();
        monthlyExpense = monthlyExpense + amount;
      }
    }

    float amountPerCategory = 0;
    // calculating amount per category and storing in map.
    for (Object categoryName : categoryNames) {

      amountPerCategory = 0;

      for (Expense e : monthlyExpenseList) {

        if (e.getCategory().getCategoryName().equals((String) categoryName)) {
          amountPerCategory = amountPerCategory + e.getAmount();
        }
      }
      amountForCategoryName.put((String) categoryName, amountPerCategory);
    }

    amountForCategoryName.put("totalExpenditure", monthlyExpense);
    System.out.println(amountForCategoryName);
    System.out.println(categoryNames);

    for (int i = 0; i < amountForCategoryName.size(); i++) {
      System.out.println();
    }

    return amountForCategoryName;
  }
}
