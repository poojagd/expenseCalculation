package com.synerzip.ExpenseCalculation;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.TableGenerator;

@Entity
@TableGenerator(name="tab", initialValue=0, allocationSize=50)
public class Category {
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE,generator="tab")
	@Column(nullable=false)
	private int id;

	@OneToMany(targetEntity = Expense.class,mappedBy="category",fetch=FetchType.LAZY)
	private List<Expense> expensesOfCategoryList=new ArrayList<Expense>();

	public void addExpenseOfCategory(Expense expense) {
		expensesOfCategoryList.add(expense);
		expense.setCategory(this);
	}
	
	public String category_name;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCategory_name() {
		return category_name;
	}

	public void setCategory_name(String category_name) {
		this.category_name = category_name;
	}

	@Override
	public String toString() {
		return "Category [id=" + id + ", category_name=" + category_name + "]";
	}

}
