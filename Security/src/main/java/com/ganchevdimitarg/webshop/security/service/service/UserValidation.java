package com.ganchevdimitarg.webshop.security.service.service;

import com.ganchevdimitarg.webshop.security.service.dto.UserServiceDTO;

public interface UserValidation {
    boolean isValid(UserServiceDTO model);

    boolean isUsernameValid(String username);

    boolean isPasswordValid(String password);

    boolean isNameValid(String name);

    boolean isAddressValid(String address);

    boolean isPhoneNumberValid(String phoneNumber);
}
