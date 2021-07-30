package com.ganchevdimitarg.webshop.security.web.api.controller;

import com.ganchevdimitarg.webshop.security.jwt.JwtConfig;
import com.ganchevdimitarg.webshop.security.service.service.UserService;
import com.ganchevdimitarg.webshop.security.web.api.model.UserRegisterControllerModel;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import javax.crypto.SecretKey;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(RegisterUserApi.class)
class RegisterUserApiTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    UserService userService;

    @MockBean
    SecretKey secretKey;

    @MockBean
    JwtConfig jwtConfig;

    @MockBean
    PasswordEncoder passwordEncoder;

    @MockBean
    ModelMapper mockMapper;

    @Test
    @DisplayName("Register user should return status created (201) if user send correct data")
    void registerUserShouldReturnStatusCreatedIfUserSendCorrectData() throws Exception {
        mockMvc
                .perform(
                        post("/v1/user/register")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"username\": \"dimitar@gmail.com\",\n" +
                                        "\"password\": \"3dG#dwd224ff\",\n" +
                                        "\"firstName\": \"Dimi-ta.r\",\n" +
                                        "\"lastName\": \"Ganchev\",\n" +
                                        "\"address\": \"Varna, Katy Paskaleva str.\",\n" +
                                        "\"phoneNumber\": \"0883375645\"}"))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andExpect(header().string("Location", Matchers.containsString("/v1/login")));
    }

    @Test
    @DisplayName("Register user should return status bad request (400) if user send incorrect data")
    void registerUserShouldReturnStatusBadRequestIfUserSendIncorrectData() throws Exception {
        UserRegisterControllerModel user = new UserRegisterControllerModel();
        mockMvc
                .perform(
                        get("/v1/user/register")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"username\": \"dimitar@gmail.com\",\n" +
                                        "\"password\": \"\",\n" +
                                        "\"firstName\": \"\",\n" +
                                        "\"lastName\": \"\",\n" +
                                        "\"address\": \"\",\n" +
                                        "\"phoneNumber\": \"\"}"))
                .andExpect(status().isMethodNotAllowed());
    }
}