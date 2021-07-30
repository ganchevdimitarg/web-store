package com.ganchevdimitarg.webshop.security.service.service;

import com.ganchevdimitarg.webshop.security.data.model.UserEntity;

public interface UserDao {
    UserEntity selectUserByUsername(String username);
}
