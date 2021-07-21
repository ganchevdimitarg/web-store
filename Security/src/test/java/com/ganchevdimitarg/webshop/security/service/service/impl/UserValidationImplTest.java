package com.ganchevdimitarg.webshop.security.service.service.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UserValidationImplTest {

    @InjectMocks
    UserValidationImpl validation;

    @Test
    @DisplayName("Should return true if username is correct")
    void shouldReturnTrueIfUsernameIsCorrect() {
        Assertions.assertAll(
                () -> assertTrue(validation.isUsernameValid("dimitar"), "Username is not empty"),
                () -> assertTrue(validation.isUsernameValid("marry"), "Username's length is equal to the minimum allowed (5)"),
                () -> assertTrue(validation.isUsernameValid("dimitarganchevwebsit"), "Username's length is equal to the maximum allowed (20)"),
                () -> assertTrue(validation.isUsernameValid("ivancka"), "Username's length is between allowed minimum and maximum (5 - 20)")
        );
    }

    @Test
    @DisplayName("Should return false if username is incorrect")
    void shouldReturnFalseIfUsernameIsIncorrect() {
        Assertions.assertAll(
                () -> assertFalse(validation.isUsernameValid(""), "Username is empty"),
                () -> assertFalse(validation.isUsernameValid(" "), "Username is empty space"),
                () -> assertFalse(validation.isUsernameValid("max"), "Username's length is less than the minimum allowed (5)"),
                () -> assertFalse(validation.isUsernameValid("maxdadasdasdsadadsdadasd"), "Username's length is more than the maximum allowed (20)")
        );
    }

    @Test
    @DisplayName("Should return true if password is correct")
    void shouldReturnTrueIfPasswordIsCorrect() {
        Assertions.assertAll(
                () -> assertTrue(validation.isUsernameValid("1qazXsw@"), "Password is not empty"),
                () -> assertTrue(validation.isUsernameValid("1qazXs"), "Password's length is equal to the minimum allowed (6)"),
                () -> assertTrue(validation.isUsernameValid("1qazXs!qazxW"), "Password's length is equal to the maximum allowed (12)"),
                () -> assertTrue(validation.isUsernameValid("1qazXsw@"), "Password's length is between allowed minimum and maximum (6 - 12)")
        );
    }

    @Test
    @DisplayName("Should return false if password is incorrect")
    void shouldReturnFalseIfPasswordIsIncorrect() {
        Assertions.assertAll(
                () -> assertFalse(validation.isPasswordValid(""), "Password is empty"),
                () -> assertFalse(validation.isPasswordValid(" "), "Password is empty space"),
                () -> assertFalse(validation.isPasswordValid("qQ1@"), "Password's length is less than the minimum allowed (6)"),
                () -> assertFalse(validation.isPasswordValid("QAsw12@!#$asdSA"), "Password's length is more than the maximum allowed (12)"),
                () -> assertFalse(validation.isPasswordValid("QA12@!#$SA"), "Password do not content least one lower letter"),
                () -> assertFalse(validation.isPasswordValid("sw12@!#$as"), "Password do not content least one upper letter"),
                () -> assertFalse(validation.isPasswordValid("QAsw@!#$"), "Password do not content least one digit"),
                () -> assertFalse(validation.isPasswordValid("QAsw12asS"), "Password do not content least one special symbol")
        );
    }

    @Test
    @DisplayName("Should return true if address is correct")
    void shouldReturnTrueIfAddressIsCorrect() {
        Assertions.assertAll(
                () -> assertTrue(validation.isAddressValid("varnas"), "Address is not empty"),
                () -> assertTrue(validation.isAddressValid("burgas"), "Address's length is equal to the minimum allowed (20)"),
                () -> assertTrue(validation.isAddressValid("bulgariavarnas"), "Address's length is equal to the maximum allowed (40)"),
                () -> assertTrue(validation.isAddressValid("ivancka"), "Address's length is between allowed minimum and maximum (20 - 40)")
        );
    }

    @Test
    @DisplayName("Should return false if address is incorrect")
    void shouldReturnFalseIfAddressIsIncorrect() {
        Assertions.assertAll(
                () -> assertFalse(validation.isAddressValid(""), "Username is empty"),
                () -> assertFalse(validation.isAddressValid(" "), "Username is empty space"),
                () -> assertFalse(validation.isAddressValid("max"), "Username's length is less than the minimum allowed (20)"),
                () -> assertFalse(validation.isAddressValid("maxdadasdWasdsadadsdadasdmaxdadasdWasdsadadsdadasd"), "Username's length is more than the maximum allowed (40)")
        );
    }

    @Test
    @DisplayName("Should return true if phone number is correct")
    void shouldReturnTrueIfPhoneNumberIsCorrect() {
        Assertions.assertAll(
                () -> assertTrue(validation.isPhoneNumberValid("0888888888"), "Phone number is not empty"),
                () -> assertTrue(validation.isPhoneNumberValid("052611109"), "Phone number's length is equal to the maximum allowed (9)"),
                () -> assertTrue(validation.isPhoneNumberValid("0888888888"), "Phone number's length is equal to the minimum allowed (10)"),
                () -> assertTrue(validation.isPhoneNumberValid("0888888888"), "Phone number contains only digits")
        );
    }

    @Test
    @DisplayName("Should return false if phone number is incorrect")
    void shouldReturnFalseIfPhoneNumberIsIncorrect() {
        Assertions.assertAll(
                () -> assertFalse(validation.isPhoneNumberValid(""), "Phone number is empty"),
                () -> assertFalse(validation.isPhoneNumberValid(" "), "Phone number is empty space"),
                () -> assertFalse(validation.isPhoneNumberValid("0888888"), "Phone number's length is less than the minimum allowed (9)"),
                () -> assertFalse(validation.isPhoneNumberValid("0888888888888"), "Phone number's length is more than the maximum allowed (10)"),
                () -> assertFalse(validation.isPhoneNumberValid("08888888a8888"), "Phone number does not contains only digits")
        );
    }

}