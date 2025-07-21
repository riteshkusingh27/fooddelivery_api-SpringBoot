package com.food.delivery.controller;

import com.food.delivery.io.UserRequest;
import com.food.delivery.io.UserResponse;
import com.food.delivery.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api")
public class UserController {
    private UserService userService;


    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse register(@RequestBody UserRequest userRequest){
                 UserResponse resp =   userService.registerUser(userRequest);
                 return resp ;
    }
}
