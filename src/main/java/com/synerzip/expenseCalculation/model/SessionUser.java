package com.synerzip.expenseCalculation.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import com.synerzip.expenseCalculation.service.UserService;

@Component
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class SessionUser {

  User user;

  @Autowired
  UserService userService;

  public User getUser(){
    user =
        userService.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public int getUserId() {
    return user.getId();
  }

  public void setUserId(int id) {
    this.user.setId(id);
  }

  public String getUserFirstName() {
    return user.getFirstName();
  }

  public void setFirstName(String firstName) {
    this.user.setFirstName(firstName);
  }

  public String getUserLastName() {
    return user.getLastName();
  }

  public void setLastName(String lastName) {
    this.user.setLastName(lastName);
  }

  public String getUserEmail() {
    return user.getEmail();
  }

  public void setUserEmail(String email) {
    this.user.setEmail(email);
  }

  public String getUserPassword() {
    return user.getPassword();
  }

  public void setUserPassword(String password) {
    this.user.setPassword(password);
  }
}
