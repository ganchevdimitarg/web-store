package com.ganchevdimitarg.webshop.security.web.api.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import static com.ganchevdimitarg.webshop.security.constant.Constants.*;

@NoArgsConstructor
@Getter
@Setter
public class UserRegisterControllerDTO {
    @Size(min = 5, max = 20, message = "Email must be between 5 and 20 characters!")
    @NotBlank(message = "Email cannot be empty!")
    @Email
    private String username;

    @Size(min = 6, max = 12, message = "Password must be between 6 and 12 characters!")
    @NotBlank(message = "Password cannot be empty!")
    @Pattern(regexp = PASSWORD_PATTERN,
            message = "a digit must occur at least once\n" +
                    "a lower case letter must occur at least once\n" +
                    "an upper case letter must occur at least once\n" +
                    "a special character must occur at least once\n" +
                    "no whitespace allowed in the entire string")
    private String password;

    @Size(min = 3, max = 12, message = "First name must be between 3 and 12 characters!")
    @NotBlank(message = "First name cannot be empty!")
    @Pattern(regexp = NAME_PATTERN,
            message = "First name must contains uppercase first letter and then lowercase letters!")
    @Pattern(regexp = WITHOUT_DIGIT_PATTERN, message = "First name cannot contains digit/digits!")
    private String firstName;

    @Size(min = 3, max = 12, message = "Last name must be between 3 and 12 characters!")
    @NotBlank(message = "Last name cannot be empty!")
    @Pattern(regexp = NAME_PATTERN,
            message = "Last name must contains uppercase first letter and then lowercase letters!")
    @Pattern(regexp = WITHOUT_DIGIT_PATTERN, message = "Last name cannot contains digit/digits!")
    private String lastName;

    @Size(min = 6, max = 40, message = "Address must be between 6 and 40 characters!")
    @NotBlank(message = "Address cannot be empty!")
    private String address;

    @Size(min = 9, max = 10, message = "Phone number must be between 9 and 10 characters!")
    @NotBlank(message = "Phone number cannot be empty!")
    @Pattern(regexp = ONLY_DIGIT_PATTERN,
            message = "Phone number must contains only digits!")
    private String phoneNumber;
}
