package com.ganchevdimitarg.webshop.security.service.service.impl;

import com.ganchevdimitarg.webshop.security.data.model.UserEntity;
import com.ganchevdimitarg.webshop.security.data.repository.UserRepository;
import com.ganchevdimitarg.webshop.security.service.model.UserServiceModel;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;

import static com.ganchevdimitarg.webshop.security.data.model.Authority.USER;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserDaoServiceImplTest {

    @Mock
    UserRepository mockUserRepository;

    @InjectMocks
    UserServiceImpl service;

    String username = "dimitar";

    @BeforeEach
    void init() {
        UserEntity user = new UserEntity(
                username,
                "1qazXsw@",
                Set.of(new SimpleGrantedAuthority(USER.name())),
                LocalDateTime.now(),
                "Dimitar",
                "Ganchev",
                "BG, Varna",
                "0888888888",
                true,
                true,
                true,
                true);
        service = new UserServiceImpl(null, mockUserRepository, new ModelMapper(), null, null);
        Mockito.when(mockUserRepository.findByUsername(username)).thenReturn(Optional.of(user));
    }

    @Test
    @DisplayName("Should select user by username")
    void shouldSelectUserByUsername() {
        UserServiceModel s = service.findByUsername(username);
        UserEntity user = mockUserRepository.findByUsername(username).get();
        assertEquals(username, user.getUsername());
    }

    @AfterEach
    void afterEachTest() {
        mockUserRepository.deleteAll();
    }
}