package com.synerzip.expenseCalculation;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.synerzip.expenseCalculation.controller.UserController;
import com.synerzip.expenseCalculation.exceptions.EmailIdExistsException;
import com.synerzip.expenseCalculation.model.User;
import com.synerzip.expenseCalculation.service.UserService;

@RunWith(SpringJUnit4ClassRunner.class)
public class UserControllerTest {


  @Bean
  public PasswordEncoder passwordEncoder() {
    return new PasswordEncoder() {

      @Override
      public String encode(CharSequence rawPassword) {
        return BCrypt.hashpw((String) rawPassword, BCrypt.gensalt(10));

      }

      @Override
      public boolean matches(CharSequence rawPassword, String encodedPassword) {
        String str = rawPassword.toString();
        return BCrypt.checkpw(str, encodedPassword);

      }

    };
  }

  private MockMvc mockmvc;

  @Mock
  private UserService userService;

  @InjectMocks
  private UserController userController;

  ObjectMapper objectMapper = new ObjectMapper();

  @Before
  public void setUp() throws Exception {

    mockmvc = MockMvcBuilders.standaloneSetup(userController).build();
  }

  User user = new User(1, "demo", "demo", "demo@gmail.com", "demo");

  @Test
  public void testCreate() throws Exception {
    String expecteduser = objectMapper.writeValueAsString(user);

    BDDMockito.given(userService.create(ArgumentMatchers.any(User.class))).willReturn(user);

    MvcResult result = mockmvc
        .perform(post("/users").accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON).content(expecteduser))
        .andExpect(status().isOk()).andExpect(jsonPath("$.firstName", Matchers.is("demo")))
        .andDo(print()).andReturn();

    JSONAssert.assertEquals(expecteduser, result.getResponse().getContentAsString(), false);
  }

  @Test
  public void testCreate_throwsBadRequestError() throws Exception {
    String expecteduser = "{\n" + "          \"firstName\": \"new\",\n"
        + "          \"lastName\" : \"new\",\n" + "          \"email\" : \"new@gmail.com\"\n"
        + "          \"password\" : \"new\"\n" + "    \n" + "}";

    BDDMockito.given(userService.create(ArgumentMatchers.any(User.class))).willReturn(user);

    mockmvc.perform(post("/users").contentType(MediaType.APPLICATION_JSON).content(expecteduser))
        .andExpect(status().isBadRequest());
  }

  @Test(expected = EmailIdExistsException.class)
  public void testCreate_throwsEmailIdExistsException() throws Exception {

    String expecteduser = objectMapper.writeValueAsString(user);

    BDDMockito.given(userService.create(user)).willThrow(EmailIdExistsException.class);

    Mockito.when(userService.create(user)).thenThrow(new EmailIdExistsException(""));

    mockmvc.perform(post("/users").contentType(MediaType.APPLICATION_JSON).content(expecteduser))
        .andExpect(status().is5xxServerError());
  }

  @Test
  public void testUpdatePassword() throws Exception {

    String encodedPassword = passwordEncoder().encode("newPassword");
    user.setPassword(encodedPassword);

    BDDMockito.given(userService.updatePassword(encodedPassword)).willReturn(user);

    String expecteduser = objectMapper.writeValueAsString(user);
    MvcResult result = mockmvc
        .perform(put("/users/password").accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON).content(encodedPassword))
        .andExpect(status().isOk()).andExpect(jsonPath("$.firstName", Matchers.is("demo")))
        .andDo(print()).andReturn();

    JSONAssert.assertEquals(expecteduser, result.getResponse().getContentAsString(), false);
  }

  @Test
  public void testUpdateFirstnameLastname() throws Exception {

    User userArgument = new User();
    userArgument.setFirstName("pooja");
    userArgument.setLastName("devray");

    String reqBody = objectMapper.writeValueAsString(userArgument);

    user.setFirstName("pooja");
    user.setLastName("devray");

    BDDMockito.given(userService.updateFirstNameAndLastName(ArgumentMatchers.any(User.class)))
        .willReturn(user);

    String expecteduser = objectMapper.writeValueAsString(user);

    MvcResult result = mockmvc
        .perform(put("/users/").accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON).content(reqBody))
        .andExpect(status().isOk()).andExpect(jsonPath("$.firstName", Matchers.is("pooja")))
        .andDo(print()).andReturn();

    System.out.println(result.getResponse().getContentAsString());

    JSONAssert.assertEquals(expecteduser, result.getResponse().getContentAsString(), false);
  }

}
