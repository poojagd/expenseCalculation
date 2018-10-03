package com.synerzip.expenseCalculation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.synerzip.expenseCalculation.service.EmailService;

@RestController
public class MailController {

  @Autowired
  EmailService emailService;

  @PostMapping(value = "/user/expenses/mail")
  public void sendMail(@RequestBody String emailId) {
    emailService.sendSimpleMessage(emailId);
  }

}
