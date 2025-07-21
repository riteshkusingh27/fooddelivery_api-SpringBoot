package com.food.delivery.service;

import com.food.delivery.io.UserRequest;
import com.food.delivery.io.UserResponse;

public interface UserService {
    UserResponse registerUser(UserRequest userRequest);
}
