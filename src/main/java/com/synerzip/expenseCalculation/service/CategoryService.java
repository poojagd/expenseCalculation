package com.synerzip.expenseCalculation.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.synerzip.expenseCalculation.model.Category;
import com.synerzip.expenseCalculation.repository.CategoryRepository;
import com.synerzip.expenseCalculation.repository.ExpenseRepository;
import com.synerzip.expenseCalculation.repository.UserRepository;

@Service
public class CategoryService {
	
	@Autowired
	UserRepository userRepository;

	@Autowired
	CategoryRepository categoryRepository;

	@Autowired
	ExpenseRepository expenseRepository;
	
	public Category findByCategoryName(String categoryName) {
		
		Category category = categoryRepository.findByCategoryName(categoryName);
		
		if(category == null) {
			Category newCategory = new Category();
			newCategory.setCategoryName(categoryName);
			category = categoryRepository.save(newCategory);
			return category;
		}
		return category;
		
	}
}
