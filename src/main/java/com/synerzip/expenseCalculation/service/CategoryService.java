package com.synerzip.expenseCalculation.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.synerzip.expenseCalculation.exceptions.CategoryNameNotFoundException;
import com.synerzip.expenseCalculation.exceptions.CategoryNotFoundException;
import com.synerzip.expenseCalculation.model.Category;
import com.synerzip.expenseCalculation.repository.CategoryRepository;

@Service
public class CategoryService {

  @Autowired
  CategoryRepository categoryRepository;

  public Category findByCategoryName(String categoryName)
      throws CategoryNotFoundException, CategoryNameNotFoundException {

    if (categoryName.equals("null") || categoryName == null || categoryName.equals("")) {
      throw new CategoryNameNotFoundException(
          "Category name is null. Please enter valid category name.");
    }

    Category category = categoryRepository.findByCategoryName(categoryName);

    if (category == null) {
      throw new CategoryNotFoundException("Category not found in the table.",
          new NullPointerException());
    }

    return category;

  }

  public Category create(Category category) {

    return categoryRepository.save(category);
  }
}
