package com.synerzip.expenseCalculation;

import static org.hamcrest.CoreMatchers.any;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.synerzip.expenseCalculation.controller.ExpenseController;
import com.synerzip.expenseCalculation.exceptions.CategoryNameNotFoundException;
import com.synerzip.expenseCalculation.model.Category;
import com.synerzip.expenseCalculation.model.Expense;
import com.synerzip.expenseCalculation.model.User;
import com.synerzip.expenseCalculation.service.ExpenseService;

@RunWith(SpringRunner.class)
@WebMvcTest(ExpenseController.class)
@AutoConfigureMockMvc(secure = false)
public class ExpenseControllerTest {

  @Autowired
  MockMvc mockmvc;

  @Autowired
  WebApplicationContext wac;

  @MockBean
  ExpenseService expenseService;

  @InjectMocks
  ExpenseController expenseController;

  String jsonExpense;

  ObjectMapper objectMapper = new ObjectMapper();

  User user = new User(1, "demo", "demo", "demo@gmail.com",
      "$2a$10$/ZjjXeF3uH.l1TMofTxky.qZzdxJP8ek7bxdg.HqTM8ktOvMUTrlu");
  Category category = new Category(1, "Electricity");

  @Before
  public void setUp() {
    mockmvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
  }

  @Test
  public void testAddExpense() throws Exception {

    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    Date convertedCurrentDate = dateFormat.parse("2013-09-18");

    Expense expense = new Expense(user, user.getId(), "demo", category, convertedCurrentDate, 10000,
        "demo", category.getCategoryName());
    expense.setId(1);
    expense.setCategoryId(1);
    expense.setDate(new Date());

    String returnedExpense = objectMapper.writeValueAsString(expense);
    System.out.println(returnedExpense);
    BDDMockito.given(expenseService.addExpense(ArgumentMatchers.any(Expense.class)))
        .willReturn(expense);

    mockmvc
        .perform(post("/user/expenses").contentType(MediaType.APPLICATION_JSON)
            .content(returnedExpense).accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk()).andExpect(jsonPath("$.id").exists())
        .andExpect(jsonPath("$.userId").value(user.getId()))
        .andExpect(jsonPath("$.categoryName").value("Electricity"))
        .andExpect(jsonPath("$.categoryId").value(category.getId())).andDo(print());
  }

  @Test(expected = CategoryNameNotFoundException.class)
  public void testAddExpense_throwsCategoryNameNotFoundException() throws Exception {

    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    Date convertedCurrentDate = dateFormat.parse("2013-09-18");

    Expense expense = new Expense(user, user.getId(), "demo", category, convertedCurrentDate, 10000,
        "demo", category.getCategoryName());
    expense.setId(1);
    expense.setCategoryId(1);
    expense.setDate(new Date());

    String returnedExpense = objectMapper.writeValueAsString(expense);
    System.out.println(returnedExpense);

    BDDMockito.given(expenseService.addExpense(expense))
        .willThrow(CategoryNameNotFoundException.class);

    Mockito.when(expenseService.addExpense(expense)).thenThrow(CategoryNameNotFoundException.class);

    mockmvc
        .perform(post("/user/expenses").contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(returnedExpense).accept(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().is5xxServerError()).andDo(print());
  }

  @Test
  public void testGetAll() throws Exception {
    Expense expense1 =
        new Expense(user, user.getId(), "demo", category, null, 15000, null, "Electricity");
    Expense expense2 =
        new Expense(user, user.getId(), "demo", category, null, 7000, "demo", "Electricity");
    Expense expense3 =
        new Expense(user, user.getId(), "demo", category, null, 900, "description", "Electricity");

    Mockito.when(expenseService.getAll()).thenReturn(Arrays.asList(expense1, expense2, expense3));

    mockmvc.perform(get("/user/expenses").contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$[0].categoryName", is("Electricity")))
        .andExpect(jsonPath("$[0].userId", is(user.getId())))
        .andExpect(jsonPath("$[0].id").exists())
        .andExpect(jsonPath("$[0].id", is(any(Integer.class))))
        .andExpect(jsonPath("$[0].user.id", is(user.getId()))).andExpect(status().isOk());

    verify(expenseService, times(1)).getAll();
  }

  @Test
  public void testDeleteById() throws Exception {

    Mockito.doNothing().when(expenseService).deleteExpense(1);

    this.mockmvc.perform(delete("/user/expenses/1").contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());
  }

  @Test
  public void testGetMonthlyExpenses() throws Exception {
    Expense expense1 =
        new Expense(user, user.getId(), "demo", category, null, 15000, null, "Electricity");
    Expense expense2 =
        new Expense(user, user.getId(), "demo", category, null, 7000, "demo", "Electricity");
    Expense expense3 =
        new Expense(user, user.getId(), "demo", category, null, 900, "description", "Electricity");

    expense1.setDate(new Date(2018, 9, 10));
    expense2.setDate(new Date(2018, 5, 10));
    expense3.setDate(new Date(2018, 3, 10));

    HashMap<String, Object> map = new HashMap<>();
    map.put("January", (float) 0.0);
    map.put("February", (float) 0.0);
    map.put("March", (float) 900);
    map.put("April", (float) 0.0);
    map.put("May", (float) 7000);
    map.put("June", (float) 0.0);
    map.put("July", (float) 0.0);
    map.put("August", (float) 0.0);
    map.put("September", (float) 15000);
    map.put("October", (float) 0.0);
    map.put("November", (float) 0.0);
    map.put("December", (float) 0.0);

    Mockito.when(expenseService.getMonthlyExpenses()).thenReturn(map);

    mockmvc.perform(get("/user/expenses/monthwise").contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk()).andExpect(jsonPath("$.January", is(0.0)))
        .andExpect(jsonPath("$.May", is(7000.0)));

    verify(expenseService, times(1)).getMonthlyExpenses();
  }

}
