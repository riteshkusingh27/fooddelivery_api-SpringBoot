package com.food.delivery.controller;


import com.food.delivery.entity.UserEntity;
import com.food.delivery.io.AuthenticationRequest;
import com.food.delivery.io.AuthenticationResponse;
import com.food.delivery.repository.Orderrepo;
import com.food.delivery.repository.UserRepo;
import com.food.delivery.service.AppUserDetailsService;
import com.food.delivery.util.JwtUtil;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final AppUserDetailsService appUserDetailsService;
    private final JwtUtil jwtUtil;
    private final UserRepo userRepo;


    @PostMapping("/login")
    public AuthenticationResponse login(@RequestBody AuthenticationRequest request){
        // password and email is captured and authenticated
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(),request.getPassword()));

     final   UserDetails user  =  appUserDetailsService.loadUserByUsername(request.getEmail());

        // generate a token and send with authenticationResponse
       UserEntity LoggedUser = userRepo.findByEmail(request.getEmail()).orElseThrow(()-> new UsernameNotFoundException("user not found "));
         String userName = LoggedUser.getName();
        final String jwtToken =  jwtUtil.generateToken(user);           ;   // create a utiltiy method to generate jwt token

        return new AuthenticationResponse(request.getEmail(),jwtToken , userName);



    }



}
