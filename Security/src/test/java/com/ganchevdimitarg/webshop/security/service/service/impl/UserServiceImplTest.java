package com.ganchevdimitarg.webshop.security.service.service.impl;

import com.ganchevdimitarg.webshop.security.data.model.UserEntity;
import com.ganchevdimitarg.webshop.security.data.repository.UserRepository;
import com.ganchevdimitarg.webshop.security.service.model.UserServiceModel;
import com.ganchevdimitarg.webshop.security.service.service.UserService;
import com.ganchevdimitarg.webshop.security.service.service.UserValidation;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    UserRepository mockUserRepository;

    @Mock
    PasswordEncoder password;

    @Mock
    ModelMapper mapper;

    @Mock
    UserValidation validation;

    @InjectMocks
    UserServiceImpl service;

    String username = "dimitar";
    UserEntity user = new UserEntity();
    UserServiceModel userModel = new UserServiceModel();

    @BeforeEach
    void setUp() {
        user.setUsername(username);
        userModel.setUsername(username);
        when(mockUserRepository.findByUsername(username)).thenReturn(Optional.of(user));
        when(mapper.map(any(), any())).thenReturn(userModel);
        when(validation.isValid(userModel)).thenReturn(true);
    }

    @Test
    void loadUserByUsername_shouldLoadUserThatExist() {

    }

    @Test
    @DisplayName("Should register user with valid data")
    void shouldRegisterUserWithValidData() {
        userModel.setUsername("dimitar");
        userModel.setPassword("1qazXsw@");
        userModel.setFirstName("Dimitar");
        userModel.setLastName("Dimitar");
        userModel.setAddress("Varna");
        userModel.setPassword("0888888888");

        service.register(userModel);

        ArgumentCaptor<UserServiceModel> argument = ArgumentCaptor.forClass(UserServiceModel.class);
        Mockito.verify(service).register(argument.capture());


        UserServiceModel user = argument.getValue();
        assertNotNull(user);
        assertEquals(2, mockUserRepository.count());
    }

    @Test
    @DisplayName("Should return user with the given username")
    void shouldReturnUserWithTheGivenUsername() {
        assertEquals(user.getUsername(), service.findByUsername(username).getUsername());
    }
}