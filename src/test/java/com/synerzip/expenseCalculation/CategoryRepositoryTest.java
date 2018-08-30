package com.synerzip.expenseCalculation;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;
import com.synerzip.expenseCalculation.model.Category;
import com.synerzip.expenseCalculation.repository.CategoryRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
public class CategoryRepositoryTest {

  @Autowired
  private TestEntityManager entityManager;

  @Autowired
  private CategoryRepository categoryRepository;

  @Test
  public void testFindByCategoryName() {
    Category category = new Category("Electricity");
    entityManager.persistAndFlush(category);

    Category foundCategory = categoryRepository.findByCategoryName(category.getCategoryName());

    assertEquals(category, foundCategory);
  }

  @Test
  public void testCreate() {
    Category category = new Category("Electricity");

    entityManager.persistAndFlush(category);

    Category foundcategory = categoryRepository.save(category);

    assertThat(foundcategory.getCategoryName()).isEqualTo(category.getCategoryName());
  }
}
