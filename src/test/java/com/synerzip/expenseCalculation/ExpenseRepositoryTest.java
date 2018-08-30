package com.synerzip.expenseCalculation;

import static org.hamcrest.CoreMatchers.hasItems;
import static org.junit.Assert.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import org.json.JSONException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import com.synerzip.expenseCalculation.model.Category;
import com.synerzip.expenseCalculation.model.Expense;
import com.synerzip.expenseCalculation.model.User;
import com.synerzip.expenseCalculation.repository.ExpenseRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
public class ExpenseRepositoryTest {

  @Autowired
  private TestEntityManager entityManager;

  @Autowired
  private ExpenseRepository expenseRepository;

  @Test
  public void testFindByUserId() throws JSONException, ParseException {

    User user = new User("demo", "demo", "demo@gmail.com", "demo");
    Category category = new Category("Electricity");
    int id = (int) entityManager.persistAndGetId(user);
    entityManager.flush();
    Expense expense1 = new Expense(user, id, "demo", category, null, 15000, null, "Electricity");
    Expense expense2 = new Expense(user, id, "demo", category, null, 7000, "demo", "Electricity");
    Expense expense3 =
        new Expense(user, id, "demo", category, null, 900, "description", "Electricity");

    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    Date convertedCurrentDate = dateFormat.parse("2013-09-18");

    expense1.setDate(convertedCurrentDate);
    expense2.setDate(convertedCurrentDate);
    expense3.setDate(convertedCurrentDate);

    entityManager.persistAndFlush(category);
    entityManager.persistAndFlush(expense1);
    entityManager.persistAndFlush(expense2);
    entityManager.persistAndFlush(expense3);

    ArrayList<Expense> expected = new ArrayList<Expense>();

    expected.add(expense1);
    expected.add(expense2);
    expected.add(expense3);

    ArrayList<Expense> foundExpenses =
        (ArrayList<Expense>) expenseRepository.findByUserId(expense1.getUserId());

    assertEquals(expected, foundExpenses);
    assertThat(foundExpenses, hasItems(expense2));

  }
}
