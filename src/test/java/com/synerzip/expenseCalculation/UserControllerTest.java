package com.synerzip.expenseCalculation;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
import org.springframework.http.MediaType;
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

}
