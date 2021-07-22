package com.ganchevdimitarg.webshop.security.constant;

public class Constants {
    public static final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{6,12}$";
    public static final String NAME_PATTERN = "^[\\p{L} ,.'-]+$";
    public static final String WITHOUT_DIGIT_PATTERN = "\\D*";
    public static final String ONLY_DIGIT_PATTERN = "^([0-9])*$";
}
