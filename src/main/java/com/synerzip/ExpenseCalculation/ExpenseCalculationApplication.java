package com.synerzip.ExpenseCalculation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.bcrypt.BCrypt;

@SpringBootApplication

public class ExpenseCalculationApplication {

	public static void main(String[] args) {
		SpringApplication.run(ExpenseCalculationApplication.class, args);
	}
}
