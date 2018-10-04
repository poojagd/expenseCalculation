package com.synerzip.expenseCalculation.service;

import java.io.StringWriter;
import java.util.Map;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import com.synerzip.expenseCalculation.model.SessionUser;
import com.synerzip.expenseCalculation.repository.ExpenseRepository;

@Service
public class EmailService {

  @Autowired
  public JavaMailSender emailSender;

  @Autowired
  public ExpenseService expenseService;

  @Autowired
  public ExpenseRepository expenseRepsoitory;

  @Autowired
  SessionUser sessionUser;

  VelocityEngine velocityEngine = new VelocityEngine();

  public void sendSimpleMessage(String to) {

    String fName = sessionUser.getUser().getFirstName();
    String lName = sessionUser.getUser().getLastName();
    Map<String, Object> map = expenseService.getMonthlyExpenses();
    float totalExpenditure = (float) map.get("January") + (float) map.get("February")
        + (float) map.get("March") + (float) map.get("January") + (float) map.get("April")
        + (float) map.get("May") + (float) map.get("June") + (float) map.get("July")
        + (float) map.get("August") + (float) map.get("September") + (float) map.get("October")
        + (float) map.get("November") + (float) map.get("December");

    map.put("totalExpenditure", totalExpenditure);
    map.put("name", fName + " " + lName);

    VelocityContext context = new VelocityContext(map);

    velocityEngine.init();
    StringWriter writer = new StringWriter();

    Template template = velocityEngine.getTemplate("./src/main/resources/templates/demo1.vm");
    template.merge(context, writer);

    String content = writer.toString();

    try {

      MimeMessage message = emailSender.createMimeMessage();
      MimeMessageHelper helper = new MimeMessageHelper(message, true);;

      helper.setText(content, true);
      helper.setTo(to);
      helper.setSubject("Your expenditure so far");
      emailSender.send(message);

    } catch (MailException e) {

      e.printStackTrace();

    } catch (MessagingException e) {

      e.printStackTrace();
    }

  }
}
