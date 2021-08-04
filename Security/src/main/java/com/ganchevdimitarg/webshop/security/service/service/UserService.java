package com.ganchevdimitarg.webshop.security.service.service;

import com.ganchevdimitarg.webshop.security.data.model.UserEntity;
import com.ganchevdimitarg.webshop.security.service.dto.UserServiceDTO;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    UserServiceDTO register(UserServiceDTO model);

    UserServiceDTO findByUsername(String username);

    UserEntity getOrCreateUser(String username, String name);
}
