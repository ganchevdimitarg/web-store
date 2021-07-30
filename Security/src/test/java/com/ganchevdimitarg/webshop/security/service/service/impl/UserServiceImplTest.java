package com.ganchevdimitarg.webshop.security.service.service.impl;

import com.ganchevdimitarg.webshop.security.data.model.UserEntity;
import com.ganchevdimitarg.webshop.security.data.repository.UserRepository;
import com.ganchevdimitarg.webshop.security.service.model.UserServiceModel;
import com.ganchevdimitarg.webshop.security.service.service.UserDao;
import com.ganchevdimitarg.webshop.security.service.service.UserValidation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {
    private String TEST_USER_NAME_EXISTS = "dimitar";
    private String TEST_USER_NAME_NOT_EXISTS = "petar";

    private UserEntity testUser;
    private UserServiceModel testUserModel;

    @Mock
    UserDao mockUserDao;

    @Mock
    UserRepository mockUserRepository;

    @Mock
    PasswordEncoder mockPasswordEncoder;

    @Mock
    ModelMapper mockMapper;

    @Mock
    UserValidation mockValidation;

    @InjectMocks
    UserServiceImpl serviceToTest;

    String username = "dimitar";

    @BeforeEach
    void setUp() {
        testUser = new UserEntity();
        testUser.setUsername(TEST_USER_NAME_EXISTS);

        testUserModel = new UserServiceModel();
        testUserModel.setUsername(TEST_USER_NAME_EXISTS);
    }

    @Test
    @DisplayName("Load user by username if user exist should load it")
    void loadUserByUsernameIfUserExistShouldLoadIt() {
        when(mockUserDao.selectUserByUsername(TEST_USER_NAME_EXISTS)).thenReturn(testUser);
        UserDetails actualUser = serviceToTest.loadUserByUsername(TEST_USER_NAME_EXISTS);

        assertEquals(testUser.getUsername(), actualUser.getUsername());
    }

    @Test
    @DisplayName("Load user by username if user not exist should throw exception")
    void loadUserByUsernameIfUserNotExistShouldThrowException() {
        when(mockUserDao.selectUserByUsername(TEST_USER_NAME_NOT_EXISTS)).thenThrow(UsernameNotFoundException.class);

        assertThrows(UsernameNotFoundException.class, () -> serviceToTest.loadUserByUsername(TEST_USER_NAME_NOT_EXISTS));
    }


    @Test
    @DisplayName("Register user with valid data should add user in data base")
    void registerUserWithValidDataShouldAddUserInDataBase() {

        when(mockMapper.map(any(), any())).thenReturn(testUserModel);
        when(mockValidation.isValid(testUserModel)).thenReturn(true);

        UserServiceModel register = serviceToTest.register(testUserModel);

        verify(mockUserRepository, times(1)).save(any(UserEntity.class));
        assertEquals(register.getUsername(), testUserModel.getUsername());
    }

    @Test
    @DisplayName("Register user with invalid data should throw exception")
    void registerUserWithInvalidShouldThrowException() {
        when(mockValidation.isValid(testUserModel)).thenReturn(false);

        assertThrows(IllegalArgumentException.class, () -> serviceToTest.register(testUserModel));
    }

    @Test
    @DisplayName("Find by username with given username should return correct user")
    void findByUsernameWithGivenUsernameShouldReturnUser() {
        when(mockUserRepository.findByUsername(TEST_USER_NAME_EXISTS)).thenReturn(Optional.of(testUser));
        when(mockMapper.map(any(), any())).thenReturn(testUserModel);
        assertEquals(testUser.getUsername(), serviceToTest.findByUsername(TEST_USER_NAME_EXISTS).getUsername());
    }
}