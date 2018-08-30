package com.synerzip.expenseCalculation;

import static org.junit.Assert.*;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;
import com.synerzip.expenseCalculation.exceptions.CategoryNameNotFoundException;
import com.synerzip.expenseCalculation.exceptions.CategoryNotFoundException;
import com.synerzip.expenseCalculation.model.Category;
import com.synerzip.expenseCalculation.repository.CategoryRepository;
import com.synerzip.expenseCalculation.service.CategoryService;

@RunWith(SpringRunner.class)
public class CategoryServiceTest {

  @TestConfiguration
  static class CategoryServiceTestContextConfiguration {

    @Bean
    public CategoryService categoryService() {
      return new CategoryService();
    }
  }

  @Autowired
  CategoryService categoryService;

  @MockBean
  CategoryRepository categoryRepository;

  Category category = new Category(1, "Electricity");

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Test
  public void testFindByCategoryName()
      throws CategoryNotFoundException, CategoryNameNotFoundException {
    
    String categoryName = "Electricity";
    Mockito.when(categoryRepository.findByCategoryName(categoryName)).thenReturn(category);

    Category foundCategory = categoryService.findByCategoryName(categoryName);
    assertEquals(category, foundCategory);
  }

  @Test(expected = CategoryNameNotFoundException.class)
  public void testFindByCategoryName_throwsCategoryNameNotFoundException()
      throws CategoryNotFoundException, CategoryNameNotFoundException {

    String categoryName = "null";

    categoryService.findByCategoryName(categoryName);
  }

  @Test(expected = CategoryNotFoundException.class)
  public void testFindByCategoryName_throwsCategoryNotFoundException()
      throws CategoryNotFoundException, CategoryNameNotFoundException {

    String categoryName = "XXXXX";

    categoryService.findByCategoryName(categoryName);
  }


  @Test
  public void testCreate() {
    Mockito.when(categoryRepository.save(category)).thenReturn(category);

    Category createdCategory = categoryService.create(category);
    assertEquals(category, createdCategory);
  }
}
