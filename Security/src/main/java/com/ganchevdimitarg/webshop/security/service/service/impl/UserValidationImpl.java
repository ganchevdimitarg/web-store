package com.ganchevdimitarg.webshop.security.service.service.impl;

import com.ganchevdimitarg.webshop.security.service.service.UserValidation;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.ganchevdimitarg.webshop.security.constant.Constants.*;

@Service
public class UserValidationImpl implements UserValidation {
    @Override
    public boolean isUsernameValid(String username) {
        return isNotBlank(username) &&
                isMinLengthIsMoreOrEqualToFive(username) &&
                isMaxLengthIsLessOrEqualToTwenty(username);
    }

    @Override
    public boolean isPasswordValid(String password) {
        return isNotBlank(password) &&
                isMinLengthIsMoreOrEqualToSix(password) &&
                isMaxLengthIsLessOrEqualToTwelve(password) &&
                isContainsLeastOnceDigitLowerUpperLetterSpecialChar(password);
    }

    @Override
    public boolean isNameValid(String name) {
        return !isNotBlank(name) ||
                !isMinLengthIsMoreOrEqualToThree(name) ||
                !isMaxLengthIsLessOrEqualToTwelve(name) ||
                !isContainsFirstUppercaseLetterAndLowercaseLetters(name) ||
                isNameContainsDigit(name);
    }

    @Override
    public boolean isAddressValid(String address) {
        return isNotBlank(address) &&
                isMinLengthIsMoreOrEqualToSix(address) &&
                isMaxLengthIsLessOrEqualToForty(address);
    }

    @Override
    public boolean isPhoneNumberValid(String phoneNumber) {
        return isNotBlank(phoneNumber) &&
                isMinLengthIsMoreOrEqualToNine(phoneNumber) &&
                isMaxLengthIsLessOrEqualToTen(phoneNumber) &&
                isContainsOnlyDigit(phoneNumber);
    }

    private boolean isNotBlank(String token) {
        return token.trim().length() > 0 && !token.equals(" ");
    }

    private boolean isMinLengthIsMoreOrEqualToThree(String name) {
        return name.trim().length() >= 3;
    }

    private boolean isMinLengthIsMoreOrEqualToFive(String username) {
        return username.trim().length() >= 5;
    }

    private boolean isMinLengthIsMoreOrEqualToSix(String token) {
        return token.trim().length() >= 6;
    }

    private boolean isMinLengthIsMoreOrEqualToNine(String phoneNumber) {
        return phoneNumber.trim().length() >= 9;
    }

    private boolean isMaxLengthIsLessOrEqualToTen(String phoneNumber) {
        return phoneNumber.trim().length() <= 10;
    }

    private boolean isMaxLengthIsLessOrEqualToTwelve(String token) {
        return token.trim().length() <= 12;
    }

    private boolean isMaxLengthIsLessOrEqualToTwenty(String username) {
        return username.trim().length() <= 20;
    }

    private boolean isMaxLengthIsLessOrEqualToForty(String address) {
        return address.trim().length() <= 40;
    }

    private boolean isContainsLeastOnceDigitLowerUpperLetterSpecialChar(String password) {
        return isTokenMatchingToCurrentPattern(PASSWORD_PATTERN, password);
    }

    private boolean isContainsFirstUppercaseLetterAndLowercaseLetters(String token) {
        return isTokenMatchingToCurrentPattern(NAME_PATTERN, token);
    }

    private boolean isNameContainsDigit(String name) {
        return isTokenMatchingToCurrentPattern(WITHOUT_DIGIT_PATTERN, name);
    }

    private boolean isContainsOnlyDigit(String phoneNumber) {
        return isTokenMatchingToCurrentPattern(ONLY_DIGIT_PATTERN, phoneNumber);
    }

    private boolean isTokenMatchingToCurrentPattern(String regex, String token) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(token);
        return matcher.find();
    }
}
