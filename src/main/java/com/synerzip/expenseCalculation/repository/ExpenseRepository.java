package com.synerzip.expenseCalculation.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.synerzip.expenseCalculation.model.Expense;

@Repository
public interface ExpenseRepository extends CrudRepository<Expense, Integer> {

}
