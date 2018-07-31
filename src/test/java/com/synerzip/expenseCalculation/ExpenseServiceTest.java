package com.synerzip.expenseCalculation;

import java.util.Date;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;
import com.synerzip.expenseCalculation.model.Category;
import com.synerzip.expenseCalculation.model.Expense;
import com.synerzip.expenseCalculation.model.User;
import com.synerzip.expenseCalculation.repository.CategoryRepository;
import com.synerzip.expenseCalculation.repository.ExpenseRepository;
import com.synerzip.expenseCalculation.repository.UserRepository;
import com.synerzip.expenseCalculation.service.ExpenseService;

@RunWith(SpringRunner.class)
public class ExpenseServiceTest {

	@MockBean
	ExpenseRepository expenseRepository;

	@MockBean
	UserRepository userRepository;

	@MockBean
	CategoryRepository categoryRepository;

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
	public void setUp() {
		User user = new User(65, "demo", "demo", "demo@gmail.com",
				"$2a$10$Owo/lBIzdFAxuVjLpdn2wulLYaaFHGDo6HVae6oiW0HXG8kSCEZdK");
		Category category = new Category(1, "Electricity");
		java.util.Date date = new Date();
		expense = new Expense();
		expense.setTitle("demo");
		expense.setUser(user);
		expense.setCategory(category);
		expense.setDate(new java.sql.Date(date.getTime()));
		expense.setAmount(1000);
		expense.setDescription("demo");

		BDDMockito.when(userRepository.findByUserId(65)).thenReturn(user);
		BDDMockito.when(categoryRepository.findByCategoryName("Electricity")).thenReturn(category);
		BDDMockito.when(expenseRepository.save(expense)).thenReturn(expense);
	}

	@Test
	public void testAddExpense() {
		BDDMockito.when(expenseService.addExpense(65, expense)).thenReturn(expense);
	}

}
