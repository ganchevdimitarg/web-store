package com.ganchevdimitarg.webshop.security.web.api.controller.model;

import com.ganchevdimitarg.webshop.security.data.model.UserEntity;
import com.ganchevdimitarg.webshop.security.data.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@Slf4j
public class HomeApi {
    private final UserRepository userRepository;

    @GetMapping("/all-user")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserEntity>> getUserAnimals() {
        List<UserEntity> user = userRepository.findAll();

        if (user.size() >0){
            log.info("Show all users");
            return ResponseEntity.ok().body(user);
        }

        return ResponseEntity.noContent().build();
    }

}
