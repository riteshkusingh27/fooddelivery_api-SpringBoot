package com.food.delivery.service;

import com.food.delivery.entity.UserEntity;
import com.food.delivery.repository.UserRepo;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@AllArgsConstructor
public class AppUserDetailsService implements UserDetailsService {
    private final UserRepo userRepo;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
      UserEntity user = userRepo.findByEmail(email).orElseThrow(()-> new UsernameNotFoundException("User with this email not found"));
//      User is an implememtation class
      return new User(user.getEmail(),user.getPassword(), Collections.emptyList());


    }
}
