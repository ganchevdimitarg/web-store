package com.ganchevdimitarg.webshop.security.service.service;

public interface UserValidation {
   boolean isUsernameValid(String username);
   boolean isPasswordValid(String password);
   boolean isNameValid(String name);
   boolean isAddressValid(String address);
   boolean isPhoneNumberValid(String phoneNumber);
}
