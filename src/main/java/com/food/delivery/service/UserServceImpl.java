package com.food.delivery.service;


import com.food.delivery.entity.UserEntity;
import com.food.delivery.io.UserRequest;
import com.food.delivery.io.UserResponse;
import com.food.delivery.repository.UserRepo;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserServceImpl implements UserService{
    UserRepo userRepo;
    @Autowired
    private  final PasswordEncoder passwordEncoder;
    @Override
    public UserResponse registerUser(UserRequest userRequest) {
      UserEntity newUser =   convertToEntity(userRequest);
      // saving user to the database
    newUser=  userRepo.save(newUser);
    // returning response
  return   convertToRespone(newUser);


    }

    private UserEntity convertToEntity(UserRequest request){
      return   UserEntity.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .name(request.getName())
                .build();
    }

    private UserResponse convertToRespone (UserEntity registerdUser){
      return  UserResponse.builder()
                .id(registerdUser.getId())
                .name(registerdUser.getName())
                .email(registerdUser.getEmail())
                .build();

    }
}
