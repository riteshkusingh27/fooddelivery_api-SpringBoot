package com.food.delivery.io;


import com.food.delivery.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter

@AllArgsConstructor
public class AuthenticationResponse {

    private String email ;
    private String token ;
    private String userName ;

}
