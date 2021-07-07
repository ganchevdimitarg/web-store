package com.ganchevdimitarg.webshop.security.service.service;

import com.ganchevdimitarg.webshop.security.data.model.UserEntity;

import java.util.Optional;

public interface UserDao {
    Optional<UserEntity> selectUserByUsername(String username);
}
