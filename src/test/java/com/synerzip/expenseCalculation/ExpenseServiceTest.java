package com.synerzip.expenseCalculation;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Answers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;
import com.synerzip.expenseCalculation.exceptions.CategoryNameNotFoundException;
import com.synerzip.expenseCalculation.exceptions.CategoryNotFoundException;
import com.synerzip.expenseCalculation.model.Category;
import com.synerzip.expenseCalculation.model.Expense;
import com.synerzip.expenseCalculation.model.SessionUser;
import com.synerzip.expenseCalculation.model.User;
import com.synerzip.expenseCalculation.repository.ExpenseRepository;
import com.synerzip.expenseCalculation.service.CategoryService;
import com.synerzip.expenseCalculation.service.ExpenseService;

@RunWith(SpringRunner.class)
public class ExpenseServiceTest {

  @TestConfiguration
  static class ExpenseServiceTestContextConfiguration {

    @Bean
    public ExpenseService expenseService() {
      return new ExpenseService();
    }
  }

  @Autowired
  ExpenseService expenseService;

  @MockBean
  ExpenseRepository expenseRepository;

  @MockBean(answer = Answers.RETURNS_DEEP_STUBS)
  SessionUser sessionUser;

  @MockBean
  CategoryService categoryService;

  User user = new User(1, "demo", "demo", "demo@gmail.com",
      "$2a$10$/ZjjXeF3uH.l1TMofTxky.qZzdxJP8ek7bxdg.HqTM8ktOvMUTrlu");

  Category category = new Category(1, "Electricity");

  @Test
  public void testAddExpense()
      throws ParseException, CategoryNotFoundException, CategoryNameNotFoundException {

    Expense expense = new Expense(user.getId(), "demo", null, null, 15000, "demo", "Electricity");
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    Date convertedCurrentDate = dateFormat.parse("2013-09-18");
    expense.setDate(convertedCurrentDate);

    Mockito.when(sessionUser.getUser().getId()).thenReturn(user.getId());

    expense.setUserId(user.getId());

    Mockito.when(categoryService.findByCategoryName(expense.getCategoryName()))
        .thenReturn(category);
    Mockito.when(categoryService.create(category)).thenReturn(category);

    expense.setCategory(category);

    Mockito.when(expenseRepository.save(expense)).thenReturn(expense);

    Expense foundexpense = expenseService.addExpense(expense);
    assertEquals(expense, foundexpense);

  }

  @Test(expected = CategoryNameNotFoundException.class)
  public void testAddExpense_throwsCategoryNameNotFoundException()
      throws ParseException, CategoryNotFoundException, CategoryNameNotFoundException {

    Expense expense = new Expense(user.getId(), "demo", null, null, 15000, "demo", "");
    expense.setUserId(user.getId());
    Mockito.when(categoryService.findByCategoryName(expense.getCategoryName()))
        .thenThrow(CategoryNameNotFoundException.class);
    expenseService.addExpense(expense);

  }

  @Test(expected = CategoryNotFoundException.class)
  public void testAddExpense_throwsCategoryNotFoundException()
      throws ParseException, CategoryNotFoundException, CategoryNameNotFoundException {
    Expense expense = new Expense(user.getId(), "demo", null, null, 15000, "demo", "newOne");
    expense.setUserId(user.getId());
    Mockito.when(categoryService.findByCategoryName(expense.getCategoryName()))
        .thenThrow(CategoryNotFoundException.class);
    categoryService.findByCategoryName(expense.getCategoryName());

  }

  @Test
  public void testGetAll() {

    Expense expense1 =
        new Expense(user, user.getId(), "demo", category, null, 15000, null, "Electricity");
    Expense expense2 =
        new Expense(user, user.getId(), "demo", category, null, 7000, "demo", "Electricity");
    Expense expense3 =
        new Expense(user, user.getId(), "demo", category, null, 900, "description", "Electricity");

    List<Expense> allExpenses = new ArrayList<Expense>();
    allExpenses.add(expense1);
    allExpenses.add(expense2);
    allExpenses.add(expense3);

    Mockito.when(sessionUser.getUser()).thenReturn(user);
    Mockito.when(expenseRepository.findByUserId(user.getId())).thenReturn(allExpenses);

    List<Expense> expenses = expenseRepository.findByUserId(user.getId());

    assertEquals(3, expenses.size());
    assertArrayEquals(expenses.toArray(), allExpenses.toArray());
    assertThat(expenses, hasItem(expense1));

  }

  @Test
  public void testDeleteById() {

    Expense expense =
        new Expense(user, user.getId(), "demo", category, null, 15000, null, "Electricity");
    expense.setId(1);

    Mockito.doNothing().when(expenseRepository).deleteById(expense.getId());

    expenseService.deleteExpense(1);

    verify(expenseRepository).deleteById(1);

  }

  @SuppressWarnings("deprecation")
  @Test
  public void testGetMonthlyExpenses() {

    Expense expense1 =
        new Expense(user, user.getId(), "demo", category, null, 15000, null, "Electricity");
    Expense expense2 =
        new Expense(user, user.getId(), "demo", category, null, 7000, "demo", "Electricity");
    Expense expense3 =
        new Expense(user, user.getId(), "demo", category, null, 900, "description", "Electricity");

    expense1.setDate(new Date(2018, 9, 10));
    expense2.setDate(new Date(2018, 5, 10));
    expense3.setDate(new Date(2018, 3, 10));


    Mockito.when(sessionUser.getUser()).thenReturn(user);
    Mockito.when(expenseRepository.findByUserId(user.getId()))
        .thenReturn(Arrays.asList(expense1, expense2, expense3));

    Map<String, Object> returnedMap = expenseService.getMonthlyExpenses();

    System.out.println(returnedMap);

    assertEquals(12, returnedMap.size());
    assertTrue(returnedMap.containsKey("January"));
  }

  @SuppressWarnings("deprecation")
  @Test
  public void testMonthlyReport() {
    Category category1 = new Category(1, "Electricity");
    Category category2 = new Category(2, "Phone");
    Category category3 = new Category(3, "Food");

    Expense expense1 =
        new Expense(user, user.getId(), "demo", category1, null, 15000, null, "Electricity");
    Expense expense2 =
        new Expense(user, user.getId(), "demo", category2, null, 7000, "demo", "Phone");
    Expense expense3 =
        new Expense(user, user.getId(), "demo", category3, null, 900, "description", "Food");
    Expense expense4 =
        new Expense(user, user.getId(), "demo", category3, null, 900, "description", "Food");

    expense1.setDate(new Date(2018, 9, 7));
    expense2.setDate(new Date(2018, 9, 1));
    expense3.setDate(new Date(2018, 9, 19));
    expense4.setDate(new Date(2018, 9, 25));

    List<Expense> expenseList = new ArrayList<Expense>();
    expenseList.add(expense1);
    expenseList.add(expense2);
    expenseList.add(expense3);
    expenseList.add(expense4);


    int monthlyExpenditure = 23800;

    Map<String, Object> amountForCategoryName = new HashMap<>();
    amountForCategoryName.put("Electricity", 15000);
    amountForCategoryName.put("Phone", 7000);
    amountForCategoryName.put("Food", 1800);
    amountForCategoryName.put("totalExpenditure", 23800);

    Mockito.when(sessionUser.getUser().getId()).thenReturn(user.getId());

    Mockito.when(expenseRepository.findByUserId(user.getId())).thenReturn(expenseList);

    Map<String, Object> returnedMap = expenseService.monthlyReport("SEP");

    System.out.println("returned map of monthly report =" + returnedMap);

    assertEquals(4, returnedMap.size());
    assertTrue(returnedMap.containsKey("Electricity"));
    assertTrue(returnedMap.containsKey("totalExpenditure"));
  }

}
