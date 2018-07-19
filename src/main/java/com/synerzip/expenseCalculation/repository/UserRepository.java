package com.synerzip.expenseCalculation.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.synerzip.expenseCalculation.model.User;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {

}
