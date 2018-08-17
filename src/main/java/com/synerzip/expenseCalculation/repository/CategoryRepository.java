package com.synerzip.expenseCalculation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.synerzip.expenseCalculation.model.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {

	@Query("Select c from Category c where c.categoryName = :categoryName")
	public Category findByCategoryName(@Param("categoryName") String categoryName);
	
	
}
