package com.synerzip.expenseCalculation;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.synerzip.expenseCalculation.controller.ExpenseController;
import com.synerzip.expenseCalculation.model.Category;
import com.synerzip.expenseCalculation.model.Expense;
import com.synerzip.expenseCalculation.service.ExpenseService;
import org.json.JSONObject;

@RunWith(SpringRunner.class)
@WebMvcTest(ExpenseController.class)
@ActiveProfiles(value = "test")
@WebAppConfiguration
@AutoConfigureMockMvc(secure = false)
public class ExpenseControllerTest {

	@Autowired
	MockMvc mockmvc;

	@MockBean
	ExpenseService expenseService;

	@MockBean
	Expense anyExpense;
	
	String jsonExpense;

	@Test
	public void testAddExpense() throws Exception {

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date convertedCurrentDate = dateFormat.parse("2013-09-18");

		Category category = new Category();
		Expense expense = new Expense();
		expense.setTitle("demo");
		expense.setDate(convertedCurrentDate);
		expense.setAmount(10000);
		expense.setDescription("demo");
		expense.setCategory(category);
		expense.getCategory().setCategoryName("Electricity");

		GsonBuilder gsonbuilder = new GsonBuilder();
		Gson gson = gsonbuilder.setPrettyPrinting().create();
		jsonExpense = gson.toJson(expense);

		JSONObject jObject = new JSONObject(jsonExpense);
		jObject.put("date", "2013-09-18");
		jsonExpense = jObject.toString();

		BDDMockito.given(expenseService.addExpense(65, expense)).willReturn(anyExpense);

		mockmvc.perform(post("/expenses/65")
						.contentType(MediaType.APPLICATION_JSON)
						.content(jsonExpense))
			   .andExpect(status().isOk());

	}

}
