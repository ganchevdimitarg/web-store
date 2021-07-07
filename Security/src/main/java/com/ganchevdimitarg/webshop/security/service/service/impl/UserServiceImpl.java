package com.ganchevdimitarg.webshop.security.service.service.impl;

import com.ganchevdimitarg.webshop.security.data.model.UserEntity;
import com.ganchevdimitarg.webshop.security.data.repository.UserRepository;
import com.ganchevdimitarg.webshop.security.service.model.UserServiceModel;
import com.ganchevdimitarg.webshop.security.service.service.UserDao;
import com.ganchevdimitarg.webshop.security.service.service.UserService;
import com.ganchevdimitarg.webshop.security.service.service.UserValidation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;

import static com.ganchevdimitarg.webshop.security.data.model.AppUserRole.ROLE_USER;

@Service
@AllArgsConstructor
@Slf4j
public class UserServiceImpl implements UserDetailsService, UserService {

    private final UserDao userDao;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final UserValidation userValidation;

    /**
     * Find user by username and load it.
     * Otherwise, return exception.
     * @param username set by the user
     * @return the intended user
     * @throws UsernameNotFoundException if username not found
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userDao
                .selectUserByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("Username %s not found", username)));
    }

    /**
     * Register user in DB.
     * @param model user model with detailed information about it
     */
    @Override
    public void register(UserServiceModel model) {
        if (!userValidation.isUsernameValid(model.getUsername()) &&
                !userValidation.isPasswordValid(model.getPassword()) &&
                !userValidation.isNameValid(model.getFirstName()) &&
                !userValidation.isNameValid(model.getLastName()) &&
                !userValidation.isAddressValid(model.getAddress()) &&
                !userValidation.isPhoneNumberValid(model.getPhoneNumber())){

            log.error("User data is not correct! Try again!");
            throw new IllegalArgumentException("User data is not correct! Try again!");
        }

        UserEntity user = new UserEntity(
                model.getUsername(),
                passwordEncoder.encode(model.getPassword()),
                Collections.singleton(new SimpleGrantedAuthority(ROLE_USER.name())),
                LocalDateTime.now(),
                model.getFirstName(),
                model.getLastName(),
                model.getAddress(),
                model.getPhoneNumber(),
                true,
                true,
                true,
                true
        );

        userRepository.save(user);

    }

    /**
     * Find user by username.
     * @param username wanted user
     * @return user
     */
    @Override
    public UserServiceModel findByUsername(String username) {
        return modelMapper.map(userRepository.findByUsername(username), UserServiceModel.class);
    }
}
