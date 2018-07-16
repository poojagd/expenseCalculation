package com.synerzip.ExpenseCalculation;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.TableGenerator;

@Entity
@TableGenerator(name="tab", initialValue=0, allocationSize=50)
public class Expense {
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE,generator="tab")
	@Column(nullable=false)
	private int id;

	//private int user_id;
	@ManyToOne()
	@JoinColumn(name = "user_id",nullable=false)
	private User user;
	public User getUser() {return user;}
	public void setUser(User user) {this.user=user;}
	
	private String title;
	//private int category_id;
	@ManyToOne
	@JoinColumn(name = "category_id")
	private Category category;
	public Category getCategory() {return category;}
	public void setCategory(Category category) {this.category=category;}

	@Column(name = "date",nullable=false)
	private java.sql.Date date;
	private float amount;
	String description;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getUser_id() {
		return user.getId();
	}

	public void setUser_id(int user_id) {
		user.setId(user_id);
	}

	public int getCategory_id() {
		return category.getId();
	}

	public void setCategory_id(int category_id) {
		category.setId(category_id);
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public java.sql.Date getDate() {
		return date;
	}

	public void setDate(java.sql.Date date) {
		this.date = date;
	}

	public float getAmount() {
		return amount;
	}

	public void setAmount(float amount) {
		this.amount = amount;
	}
	@Override
	public String toString() {
		return "Expense [id=" + id + ", title=" + title + ", category=" + category + ", user=" + user + ", date=" + date
				+ ", amount=" + amount + ", description=" + description + "]";
	}

	
}
