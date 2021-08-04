package com.ganchevdimitarg.webshop.security.service.service.impl;

import com.ganchevdimitarg.webshop.security.data.model.UserEntity;
import com.ganchevdimitarg.webshop.security.data.repository.UserRepository;
import com.ganchevdimitarg.webshop.security.service.dto.UserServiceDTO;
import com.ganchevdimitarg.webshop.security.service.service.UserDao;
import com.ganchevdimitarg.webshop.security.service.service.UserService;
import com.ganchevdimitarg.webshop.security.service.service.UserValidation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;

import static com.ganchevdimitarg.webshop.security.data.model.AppUserRole.ROLE_USER;

@Service
@AllArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserDao userDao;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final UserValidation userValidation;

    /**
     * Find user by username and load it.
     * Otherwise, return exception.
     *
     * @param username set by the user
     * @return the intended user
     * @throws UsernameNotFoundException if username not found
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userDao.selectUserByUsername(username);
    }

    /**
     * Save user in DB.
     *
     * @param model user model with detailed information about it
     * @return saved user
     */
    @Override
    public UserServiceDTO register(UserServiceDTO model) {
        if (!userValidation.isValid(model)) {
            log.error("User data is not correct! Try again!");
            throw new IllegalArgumentException("User data is not correct! Try again!");
        }

        if (userRepository.findByUsername(model.getUsername()).isPresent()) {
            log.error("User exist!");
            throw new IllegalArgumentException("User should not exist!");
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

        return modelMapper.map(userRepository.save(user), UserServiceDTO.class);
    }

    /**
     * Find user by username.
     *
     * @param username wanted user
     * @return user
     */
    @Override
    public UserServiceDTO findByUsername(String username) {
        return modelMapper.map(userRepository.findByUsername(username), UserServiceDTO.class);
    }

    /**
     * Search for user by username and if it not exists create new one
     *
     * @param username
     * @return find user or new user
     */
    @Override
    public UserEntity getOrCreateUser(String username, String name) {
        if (username.isBlank()) {
            throw new IllegalArgumentException("Username cannot be empty!");
        }

        Optional<UserEntity> user = userRepository.findByUsername(username);

        return user.orElseGet(() -> createUser(username, name));
    }

    /**
     * Create new user
     *
     * @param username
     * @return new user
     */
    private UserEntity createUser(String username, String name) {
        log.info("Creating a new user with email [GDPR]");

        String[] userNames = name.split("\\s+");

        UserEntity user = new UserEntity();
        user.setUsername(username);
        user.setGrantedAuthorities(Collections.singleton(new SimpleGrantedAuthority(ROLE_USER.name())));
        user.setRegisterDateTime(LocalDateTime.now());
        user.setFirstName(userNames[0]);
        user.setLastName(userNames[1]);
        user.setAccountNonExpired(true);
        user.setAccountNonLocked(true);
        user.setCredentialsNonExpired(true);
        user.setEnabled(true);

        userRepository.save(user);

        return user;
    }

}
