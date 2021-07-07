package com.ganchevdimitarg.webshop.security.data.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Set;

@Document(value = "users")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserEntity extends BaseEntity implements Serializable, UserDetails {
    @Indexed(unique = true)
    @Size(min = 5, max = 20, message = "Email must be between 5 and 20 characters!")
    @NotBlank(message = "Email can not be empty!")
    @Email
    private String username;

    @Size(min = 6, max = 12, message = "Password must be between 6 and 12 characters!")
    @NotBlank(message = "Password can not be empty!")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{6,12}$",
            message = "a digit must occur at least once\n" +
                    "a lower case letter must occur at least once\n" +
                    "an upper case letter must occur at least once\n" +
                    "a special character must occur at least once\n" +
                    "no whitespace allowed in the entire string")
    private String password;

    private Set<? extends GrantedAuthority> grantedAuthorities;
    private LocalDateTime registerDateTime;

    @Size(min = 3, max = 12, message = "First name must be between 3 and 12 characters!")
    @NotBlank(message = "First name can not be empty!")
    @Pattern(regexp = "^([A-Z])(\\p{L})(?=\\S+$).{3,12}$",
            message = "First name must contains uppercase first letter and then lowercase letters!")
    @Pattern(regexp = "\\D*", message = "First name cannot contains digit/digits!")
    private String firstName;

    @Size(min = 3, max = 12, message = "Last name must be between 3 and 12 characters!")
    @NotBlank(message = "Last name can not be empty!")
    @Pattern(regexp = "^([A-Z])(\\p{L})(?=\\S+$).{3,12}$",
            message = "Last name must contains uppercase first letter and then lowercase letters!")
    @Pattern(regexp = "\\D*", message = "Last name cannot contains digit/digits!")
    private String lastName;

    @Size(min = 6, max = 40, message = "Address must be between 6 and 40 characters!")
    @NotBlank(message = "Address can not be empty!")
    private String address;

    @Size(min = 9, max = 10, message = "Phone number must be between 9 and 10 characters!")
    @NotBlank(message = "Phone number can not be empty!")
    @Pattern(regexp = "^([0-9])*$",
            message = "Phone number must contains only digits!")
    private String phoneNumber;

    private boolean isAccountNonExpired;
    private boolean isAccountNonLocked;
    private boolean isCredentialsNonExpired;
    private boolean isEnabled;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return grantedAuthorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return isAccountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return isAccountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return isEnabled;
    }

    @Override
    public boolean isEnabled() {
        return isEnabled;
    }

}


