package com.ganchevdimitarg.webshop.security.service.service;

import com.ganchevdimitarg.webshop.security.service.model.UserServiceModel;

public interface UserValidation {
   boolean isValid(UserServiceModel model);
   boolean isUsernameValid(String username);
   boolean isPasswordValid(String password);
   boolean isNameValid(String name);
   boolean isAddressValid(String address);
   boolean isPhoneNumberValid(String phoneNumber);
}
