package com.ganchevdimitarg.webshop.security.web.api.controller;

import com.ganchevdimitarg.webshop.security.hendler.OAuth2UserAuthSuccessHandler;
import com.ganchevdimitarg.webshop.security.jwt.JwtConfig;
import com.ganchevdimitarg.webshop.security.service.dto.UserServiceDTO;
import com.ganchevdimitarg.webshop.security.service.service.UserService;
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
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import javax.crypto.SecretKey;

import static org.mockito.Mockito.when;
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

    @MockBean
    OAuth2UserAuthSuccessHandler oAuth2UserAuthSuccessHandler;

    @MockBean
    ClientRegistrationRepository clientRegistrationRepository;

    @Test
    @DisplayName("Register user should return status created (201) if user send correct data")
    void registerUserShouldReturnStatusCreatedIfUserSendCorrectData() throws Exception {
        mockMvc
                .perform(
                        post("/v1/register")
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
    @DisplayName("Register user should return status method not allowed (405) if user send incorrect data")
    void registerUserShouldReturnStatusMethodNotAllowedIfUserSendIncorrectData() throws Exception {
        mockMvc
                .perform(
                        get("/v1/register")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"username\": \"dimitar@gmail.com\",\n" +
                                        "\"password\": \"\",\n" +
                                        "\"firstName\": \"\",\n" +
                                        "\"lastName\": \"\",\n" +
                                        "\"address\": \"\",\n" +
                                        "\"phoneNumber\": \"\"}"))
                .andExpect(status().isMethodNotAllowed());
    }

    @Test
    @DisplayName("Register user should return status method not allowed (405) if user already exist")
    void registerUserShouldReturnStatusMethodNotAllowed405IfUserAlreadyExist() throws Exception {
        UserServiceDTO user = new UserServiceDTO();
        when(userService.register(user)).thenReturn(user);

        mockMvc
                .perform(
                        get("/v1/register")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"username\": \"dimitar@gmail.com\",\n" +
                                        "\"password\": \"3dG#dwd224ff\",\n" +
                                        "\"firstName\": \"Dimi-ta.r\",\n" +
                                        "\"lastName\": \"Ganchev\",\n" +
                                        "\"address\": \"Varna, Katy Paskaleva str.\",\n" +
                                        "\"phoneNumber\": \"0883375645\"}"))
                .andExpect(status().isMethodNotAllowed());
    }
}