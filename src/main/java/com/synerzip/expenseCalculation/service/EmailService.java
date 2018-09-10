package com.synerzip.expenseCalculation.service;

import java.util.HashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

  @Autowired
  public JavaMailSender emailSender;

  @Autowired
  public ExpenseService expenseService;

  public void sendSimpleMessage(String to) throws MailException {

    HashMap<String, Float> map = expenseService.getMonthlyExpenses();
    String text = map.toString();
    SimpleMailMessage message = new SimpleMailMessage();
    message.setTo(to);
    message.setFrom("poojadevray@gmail.com");
    message.setSubject("Your expenditure so far");
    message.setText(text);
    emailSender.send(message);

  }
}
