package com.food.delivery.config;



import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.List;

@Configuration
@EnableWebSecurity
public class Securityconfig {

    @Bean
    public SecurityFilterChain sfc (HttpSecurity http) throws Exception {
        http.cors(Customizer.withDefaults())
                .csrf(Customizer.withDefaults())
                .authorizeHttpRequests(auth ->auth.requestMatchers("/api/register","/api/login","/api/foods/**").permitAll().anyRequest().authenticated())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
                return http.build();

    }
  @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    //corse filter
    @Bean
    public CorsFilter corsFilter(CorsConfigurationSource corsConfigurationSource){
        return new CorsFilter(corsConfigurationSource());

    }

    private UrlBasedCorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowedOrigins(List.of("http://localhost:5173" , "http://localhost:5174"));
        corsConfiguration.setAllowedMethods(List.of("GET","POST","PUT","DELETE","OPTIONS"));
        corsConfiguration.setAllowedHeaders(List.of("Authorization" , "Content-Type"));
        corsConfiguration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);
        return source;
    }


}
