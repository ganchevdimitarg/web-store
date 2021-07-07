package com.ganchevdimitarg.webshop.security.service.service;

import com.ganchevdimitarg.webshop.security.service.model.UserServiceModel;

public interface UserService {

    void register(UserServiceModel model);

    UserServiceModel findByUsername(String username);
}
