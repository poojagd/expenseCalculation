package com.synerzip.expenseCalculation.model;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.format.annotation.NumberFormat.Style;
import com.synerzip.expenseCalculation.model.Category;

@Entity
@Table(name = "expense")
public class Expense {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private int id;

  @ManyToOne
  @JoinColumn(name = "user_id", nullable = false, insertable = false, updatable = false)
  private User user;

  @Column(name = "user_id")
  private int userId;

  private String title;

  @ManyToOne
  @JoinColumn(name = "category_id")
  private Category category;

  @Temporal(TemporalType.DATE)
  @Column(name = "date", nullable = false)
  @NotNull(message = "Date should not be null.")
  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
  private java.util.Date date;

  @NumberFormat(style = Style.NUMBER)
  private float amount;

  private String description;

  @Transient
  private String categoryName;

  public Expense() {}

  public Expense(int id, User user, String title, Category category, Date date, float amount,
      String description) {
    super();
    this.id = id;
    this.user = user;
    this.title = title;
    this.category = category;
    this.date = date;
    this.amount = amount;
    this.description = description;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public Category getCategory() {
    return category;
  }

  public void setCategory(Category category) {
    this.category = category;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public int getUserId() {
    return userId;
  }

  public void setUserId(int userId) {
    this.userId = userId;
  }

  public int getCategoryId() {
    return this.category.getId();
  }

  public void setCategoryId(int categoryId) {
    this.category.setId(categoryId);
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

  public java.util.Date getDate() {

    return date;
  }

  public void setDate(java.util.Date date) {

    this.date = date;
  }

  public float getAmount() {
    return amount;
  }

  public void setAmount(float amount) {
    this.amount = amount;
  }

  public String getCategoryName() {
    return categoryName;
  }

  public void setCategoryName(String categoryName) {
    this.categoryName = categoryName;
  }

  @Override
  public String toString() {
    return "Expense [id=" + id + ", user=" + user + ", userId=" + userId + ", title=" + title
        + ", category=" + category + ", date=" + date + ", amount=" + amount + ", description="
        + description + ", categoryName=" + categoryName + "]";
  }



}
