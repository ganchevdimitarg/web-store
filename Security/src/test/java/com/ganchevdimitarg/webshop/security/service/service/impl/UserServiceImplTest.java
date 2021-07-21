package com.ganchevdimitarg.webshop.security.service.service.impl;

import com.ganchevdimitarg.webshop.security.data.model.UserEntity;
import com.ganchevdimitarg.webshop.security.data.repository.UserRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    UserRepository repository;

    @Test
    void loadUserByUsername_shouldLoadUserThatExist() {
        UserEntity user = new UserEntity();

    }

    @Test
    void register() {

    }

    @Test
    void findByUsername() {
    }
}