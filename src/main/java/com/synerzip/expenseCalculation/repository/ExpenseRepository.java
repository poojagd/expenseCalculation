package com.synerzip.expenseCalculation.repository;

import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.synerzip.expenseCalculation.model.Expense;

@Repository
public interface ExpenseRepository extends CrudRepository<Expense, Integer> {

  @Query("Select e from Expense e where e.userId = :userId")
  public List<Expense> findByUserId(@Param("userId") int userId);
}
