package com.synerzip.expenseCalculation;

import java.util.ArrayList;
import java.util.Date;
import org.junit.Before;
import org.junit.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import com.synerzip.expenseCalculation.model.Category;
import com.synerzip.expenseCalculation.model.Expense;
import com.synerzip.expenseCalculation.model.User;
import com.synerzip.expenseCalculation.repository.ExpenseRepository;
import com.synerzip.expenseCalculation.repository.UserRepository;
import com.synerzip.expenseCalculation.service.CategoryService;
import com.synerzip.expenseCalculation.service.ExpenseService;
import com.synerzip.expenseCalculation.service.UserService;

public class ExpenseServiceTest {

	@MockBean
	ExpenseRepository expenseRepository;

	@MockBean
	UserRepository userRepository;
	
	@MockBean
	UserService userService;

	@MockBean
	CategoryService categoryService;

	@MockBean
	Expense expense;

	@Autowired
	ExpenseService expenseService;

	TestEntityManager entityManager;

	@TestConfiguration
	static class ExpenseServiceTestConfiguration {

		@Bean
		public ExpenseService expenseService() {
			return new ExpenseService();
		}
	}

	@Before
	public void setUp(){
		
		User user = new User(1, "demo", "demo", "demo@gmail.com",
				"$2a$10$/ZjjXeF3uH.l1TMofTxky.qZzdxJP8ek7bxdg.HqTM8ktOvMUTrlu");
		Category category = new Category(1, "Electricity");
		java.util.Date date = new Date();
		expense = new Expense();
		expense.setTitle("demo");
		expense.setUser(user);
		expense.setCategory(category);
		expense.setDate(new java.sql.Date(date.getTime()));
		expense.setAmount(1000);
		expense.setDescription("demo");

		BDDMockito.when(userService.findByEmail("demo@gmail.com")).thenReturn(user);
		BDDMockito.when(categoryService.findByCategoryName("Electricity")).thenReturn(category);
		BDDMockito.when(expenseRepository.save(expense)).thenReturn(expense);
		BDDMockito.when(expenseRepository.findAllByUserId(1)).thenReturn(new ArrayList<Expense>());
	}

	@Test
	public void testAddExpense() {
		BDDMockito.when(expenseService.addExpense(expense)).thenReturn(expense);
	}
	
	@Test
	public void testGetAll() {
		BDDMockito.when(expenseService.getAll()).thenReturn(new ArrayList<Expense>());
	}

}
