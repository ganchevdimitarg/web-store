package com.ganchevdimitarg.webshop.security.web.api.controller;

import com.ganchevdimitarg.webshop.security.service.dto.UserServiceDTO;
import com.ganchevdimitarg.webshop.security.service.service.UserService;
import com.ganchevdimitarg.webshop.security.web.api.dto.UserRegisterControllerDTO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@AllArgsConstructor
@Slf4j
public class RegisterUserApi {
    private final UserService userService;
    private final ModelMapper modelMapper;

    @PostMapping(value = "/v1/register",
            consumes = APPLICATION_JSON_VALUE,
            headers = "Accept=application/json")
    public ResponseEntity<UserRegisterControllerDTO> registerUser(
            @Valid @RequestBody UserRegisterControllerDTO user,
            BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach(e -> log.error(e.getDefaultMessage()));
            return ResponseEntity
                    .status(HttpStatus.METHOD_NOT_ALLOWED)
                    .header(HttpHeaders.LOCATION, "/v1/register")
                    .build();
        }

        try {
            userService.register(modelMapper.map(user, UserServiceDTO.class));
        } catch (IllegalArgumentException e) {
            log.error(e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.METHOD_NOT_ALLOWED)
                    .header(HttpHeaders.LOCATION, "/v1/register")
                    .build();
        }

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .header(HttpHeaders.LOCATION, "/v1/login")
                .build();
    }
}
