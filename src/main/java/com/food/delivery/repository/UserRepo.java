package com.food.delivery.repository;

import com.food.delivery.entity.UserEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepo extends MongoRepository<UserEntity,String> {

    Optional<UserEntity> findByEmail(String email);
}
