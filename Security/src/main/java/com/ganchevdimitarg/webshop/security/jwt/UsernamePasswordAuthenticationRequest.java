package com.ganchevdimitarg.webshop.security.jwt;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class UsernamePasswordAuthenticationRequest {
    private String username;
    private String password;
}
