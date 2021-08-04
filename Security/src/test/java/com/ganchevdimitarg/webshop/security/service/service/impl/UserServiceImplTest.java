package com.ganchevdimitarg.webshop.security.service.service.impl;

import com.ganchevdimitarg.webshop.security.data.model.UserEntity;
import com.ganchevdimitarg.webshop.security.data.repository.UserRepository;
import com.ganchevdimitarg.webshop.security.service.dto.UserServiceDTO;
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
    private String TEST_USER_NAME_VALID = "dimitar";
    private String TEST_USER_NAME_INVALID = "petar";

    private UserEntity testUser;
    private UserServiceDTO testUserModel;

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

    @BeforeEach
    void setUp() {
        testUser = new UserEntity();
        testUser.setUsername(TEST_USER_NAME_VALID);

        testUserModel = new UserServiceDTO();
        testUserModel.setUsername(TEST_USER_NAME_VALID);
    }

    @Test
    @DisplayName("Load user by username if user exist should load it")
    void loadUserByUsernameIfUserExistShouldLoadIt() {
        when(mockUserDao.selectUserByUsername(TEST_USER_NAME_VALID)).thenReturn(testUser);
        UserDetails actualUser = serviceToTest.loadUserByUsername(TEST_USER_NAME_VALID);

        assertEquals(testUser.getUsername(), actualUser.getUsername());
    }

    @Test
    @DisplayName("Load user by username if user not exist should throw exception")
    void loadUserByUsernameIfUserNotExistShouldThrowException() {
        when(mockUserDao.selectUserByUsername(TEST_USER_NAME_INVALID)).thenThrow(UsernameNotFoundException.class);

        assertThrows(UsernameNotFoundException.class, () -> serviceToTest.loadUserByUsername(TEST_USER_NAME_INVALID));
    }


    @Test
    @DisplayName("Register user with valid data should add user in data base")
    void registerUserWithValidDataShouldAddUserInDataBase() {

        when(mockMapper.map(any(), any())).thenReturn(testUserModel);
        when(mockValidation.isValid(testUserModel)).thenReturn(true);
        when(mockUserRepository.findByUsername(TEST_USER_NAME_VALID)).thenReturn(Optional.empty());

        UserServiceDTO register = serviceToTest.register(testUserModel);

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
    @DisplayName("Register user with exist username should throw exception")
    void registerUserWithExistUsernameShouldThrowException() {
        when(mockValidation.isValid(testUserModel)).thenReturn(true);
        when(mockUserRepository.findByUsername(TEST_USER_NAME_VALID)).thenReturn(Optional.of(testUser));

        assertThrows(IllegalArgumentException.class, () -> serviceToTest.register(testUserModel));
    }

    @Test
    @DisplayName("Find by username with given username should return correct user")
    void findByUsernameWithGivenUsernameShouldReturnUser() {
        when(mockUserRepository.findByUsername(TEST_USER_NAME_VALID)).thenReturn(Optional.of(testUser));
        when(mockMapper.map(any(), any())).thenReturn(testUserModel);
        assertEquals(testUser.getUsername(), serviceToTest.findByUsername(TEST_USER_NAME_VALID).getUsername());
    }

    @Test
    @DisplayName("Get or create user by username should return exist user")
    void getOrCreateUserByUsernameShouldReturnExistUser() {
        when(mockUserRepository.findByUsername(TEST_USER_NAME_VALID)).thenReturn(Optional.of(testUser));

        assertEquals(testUser.getUsername(),
                serviceToTest.getOrCreateUser(TEST_USER_NAME_VALID, TEST_USER_NAME_VALID).getUsername());
    }

    @Test
    @DisplayName("Get or create user by username should create new user when not exist")
    void getOrCreateUserByUsernameShouldCreateNewUserWhenNotExist() {
        when(mockUserRepository.findByUsername(TEST_USER_NAME_VALID)).thenReturn(Optional.empty());

        assertEquals(testUser.getUsername(),
                serviceToTest.getOrCreateUser(TEST_USER_NAME_VALID, "Dimitar Ganchev").getUsername());
    }

    @Test
    @DisplayName("Get or create user by username should throw exception when username is blank")
    void getOrCreateUserByUsernameShouldThrowExceptionWhenUsernameIsBlank() {
        assertThrows(IllegalArgumentException.class, () -> serviceToTest.register(new UserServiceDTO()));
    }
}