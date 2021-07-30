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
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserDaoServiceImplTest {
    private String TEST_USER_NAME_EXISTS = "dimitar";
    private String TEST_USER_NAME_NOT_EXISTS = "petar";

    @Mock
    UserRepository mockUserRepository;

    @InjectMocks
    UserDaoServiceImpl userDaoService;

    UserEntity user;

    @BeforeEach
    void setUp() {
        user = new UserEntity();
        user.setUsername(TEST_USER_NAME_EXISTS);
    }

    @AfterEach
    void afterEachTest() {
        mockUserRepository.deleteAll();
    }

    @Test
    @DisplayName("Select user by username should return user with given username")
    void selectUserByUsernameShouldReturnUserWithGivenUsername() {
        when(mockUserRepository.findByUsername(TEST_USER_NAME_EXISTS)).thenReturn(Optional.of(user));

        assertEquals(TEST_USER_NAME_EXISTS, userDaoService.selectUserByUsername(TEST_USER_NAME_EXISTS).getUsername());
    }

    @Test
    @DisplayName("Select user by username should throw exception if username did not  found")
    void selectUserByUsernameShouldThrowExceptionIfUsernameDidNotFound() {
        when(mockUserRepository.findByUsername(TEST_USER_NAME_NOT_EXISTS)).thenThrow(UsernameNotFoundException.class);

        assertThrows(UsernameNotFoundException.class, () -> userDaoService.selectUserByUsername(TEST_USER_NAME_NOT_EXISTS));
    }
}