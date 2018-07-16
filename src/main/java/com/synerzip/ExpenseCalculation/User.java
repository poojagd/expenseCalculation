package com.synerzip.ExpenseCalculation;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

import org.hibernate.annotations.NamedQueries;

@Entity
@NamedQuery(name = "findByEmail_id", query = "SELECT u FROM User u WHERE u.email_id= :email_id")
// tableGenerator is used to start the id field with 1.
@TableGenerator(name = "tab", initialValue = 0, allocationSize = 50)
@Table(name = "user")
public class User {

	public User() {
	}

	public User(int id, String first_name, String last_name, String email_id, String password) {
		super();
		this.id = id;
		this.first_name = first_name;
		this.last_name = last_name;
		this.email_id = email_id;
		this.password = password;

	}

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "tab")
	@Column(nullable = false)
	private int id;

	private String first_name;
	private String last_name;

	@Column(unique = true, nullable = false)
	String email_id;

	@Column(nullable = false)
	String password;

	@OneToMany(targetEntity = Expense.class, mappedBy = "user", fetch = FetchType.LAZY)
	private List<Expense> expensesList = new ArrayList<Expense>();

	public void addExpense(Expense expense) {
		expensesList.add(expense);
		expense.setUser(this);
	}

	public String getFirst_name() {
		return first_name;
	}

	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}

	public String getLast_name() {
		return last_name;
	}

	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}

	public String getEmail_id() {
		return email_id;
	}

	public void setEmail_id(String email_id) {
		this.email_id = email_id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "User [first_name=" + first_name + ", last_name=" + last_name + ", email_id=" + email_id + ", password="
				+ password + ", id=" + id + "]";
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (email_id == null) {
			if (other.email_id != null)
				return false;
		} else if (!email_id.equals(other.email_id))
			return false;
		if (expensesList == null) {
			if (other.expensesList != null)
				return false;
		} else if (!expensesList.equals(other.expensesList))
			return false;
		if (first_name == null) {
			if (other.first_name != null)
				return false;
		} else if (!first_name.equals(other.first_name))
			return false;
		if (id != other.id)
			return false;
		if (last_name == null) {
			if (other.last_name != null)
				return false;
		} else if (!last_name.equals(other.last_name))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		return true;
	}

}
