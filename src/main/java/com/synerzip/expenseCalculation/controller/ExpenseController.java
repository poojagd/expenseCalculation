package com.synerzip.expenseCalculation.controller;

import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.synerzip.expenseCalculation.exceptions.CategoryNameNotFoundException;
import com.synerzip.expenseCalculation.model.Expense;
import com.synerzip.expenseCalculation.service.ExpenseService;

@RestController
@RequestMapping(path = "/user/expenses")
public class ExpenseController {

  @Autowired
  ExpenseService expenseService;

  @PostMapping(consumes = "application/json"  , produces = "application/json")
  public Expense addExpense(@Valid @RequestBody Expense expense)
      throws CategoryNameNotFoundException {
    return expenseService.addExpense(expense);
  }

  @GetMapping
  public List<Expense> viewAll() {
    return expenseService.getAll();

  }
}
