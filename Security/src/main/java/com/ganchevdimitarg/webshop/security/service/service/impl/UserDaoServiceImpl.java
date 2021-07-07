package com.ganchevdimitarg.webshop.security.service.service.impl;

import com.ganchevdimitarg.webshop.security.data.model.UserEntity;
import com.ganchevdimitarg.webshop.security.data.repository.UserRepository;
import com.ganchevdimitarg.webshop.security.service.service.UserDao;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserDaoServiceImpl implements UserDao {

    private final UserRepository userRepository;

    @Override
    public Optional<UserEntity> selectUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

}
