package com.ganchevdimitarg.webshop.security.data.repository;

import com.ganchevdimitarg.webshop.security.data.model.UserEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<UserEntity, String> {
    Optional<UserEntity> findByUsername(String username);

}
