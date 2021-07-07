package com.ganchevdimitarg.webshop.security.service.service;

import com.ganchevdimitarg.webshop.security.data.model.Authority;
import com.ganchevdimitarg.webshop.security.data.model.UserEntity;
import com.ganchevdimitarg.webshop.security.data.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class UsersDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserEntity> userEntityOpt = userRepository.findByUsername(username);

        return userEntityOpt
                .map(this::mapToUserDetails)
                .orElseThrow(()->new UsernameNotFoundException("User " + username + " not found!"));
    }

    private UserDetails mapToUserDetails(UserEntity user){
        return new User(
                user.getUsername(),
                user.getPassword(),
                user.getAuthority()
                        .stream()
                        .map(this::mapToGrantedAuthority)
                        .collect(Collectors.toList()));
    }

    private GrantedAuthority mapToGrantedAuthority(Authority authority){
        return new SimpleGrantedAuthority(authority.toString());
    }
}
