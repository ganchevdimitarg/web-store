package com.ganchevdimitarg.webshop.security.service.service.impl;

import com.ganchevdimitarg.webshop.security.data.model.UserEntity;
import com.ganchevdimitarg.webshop.security.data.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserDaoServiceImplTest {

    @Mock
    UserRepository mockUserRepository;

    @InjectMocks
    UserDaoServiceImpl userDaoService;

    String username = "dimitar";
    UserEntity user = new UserEntity();

    @BeforeEach
    void setUp() {
        user.setUsername(username);
        when(mockUserRepository.findByUsername(username)).thenReturn(Optional.of(user));
    }

    @AfterEach
    void afterEachTest() {
        mockUserRepository.deleteAll();
    }

    @Test
    @DisplayName("Should select user by username")
    void shouldSelectUserByUsername() {
        assertEquals(username, userDaoService.selectUserByUsername(username).get().getUsername());
    }
}