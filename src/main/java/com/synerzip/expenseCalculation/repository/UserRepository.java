package com.synerzip.expenseCalculation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.synerzip.expenseCalculation.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

	@Query("Select u from User u where u.id = :userId")
	public User findByUserId(@Param("userId") int userId);
	
	@Query("Select u from User u where u.email = :email")
	public User findByEmail(@Param("email") String email);

}
