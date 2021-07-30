package com.ganchevdimitarg.webshop.security.service.service;

import com.ganchevdimitarg.webshop.security.service.model.UserServiceModel;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    UserServiceModel register(UserServiceModel model);

    UserServiceModel findByUsername(String username);
}
