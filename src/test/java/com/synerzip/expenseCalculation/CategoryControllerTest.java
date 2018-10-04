package com.synerzip.expenseCalculation;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.synerzip.expenseCalculation.controller.CategoryController;
import com.synerzip.expenseCalculation.service.CategoryService;

@RunWith(SpringJUnit4ClassRunner.class)
public class CategoryControllerTest {

  private MockMvc mockmvc;

  @Mock
  private CategoryService categoryService;

  @InjectMocks
  private CategoryController categoryController;

  ObjectMapper objectMapper = new ObjectMapper();

  @Before
  public void setUp() throws Exception {

    mockmvc = MockMvcBuilders.standaloneSetup(categoryController).build();
  }

  @Test
  public void testGetCategoryNames() throws Exception {

    List<String> categoryNames = new ArrayList<>();
    categoryNames.add("Electricity");
    categoryNames.add("Mobile");
    categoryNames.add("Food");

    Mockito.when(categoryService.getAllCategories()).thenReturn(categoryNames);

    mockmvc.perform(get("/category/names")).andExpect(status().isOk())
        .andExpect(jsonPath("$[0]", is("Electricity")));
  }
}
