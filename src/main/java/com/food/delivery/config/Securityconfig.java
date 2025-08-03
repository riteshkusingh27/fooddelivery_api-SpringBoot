package com.food.delivery.config;



import com.food.delivery.filters.JwtAuthFilter;
import com.food.delivery.service.AppUserDetailsService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import software.amazon.awssdk.core.internal.http.pipeline.stages.ApplyUserAgentStage;

import java.util.List;

@Configuration
@EnableWebSecurity

public class Securityconfig {
    private final JwtAuthFilter jwtAuthFilter;

    // user detail service
    private final AppUserDetailsService auth;

    public Securityconfig(JwtAuthFilter jwtAuthFilter, AppUserDetailsService auth) {
        this.jwtAuthFilter = jwtAuthFilter;
        this.auth = auth;
    }

    @Bean
    public SecurityFilterChain sfc (HttpSecurity http) throws Exception {
        http.cors(Customizer.withDefaults())
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth ->auth.requestMatchers("/api/register","/api/login", "/api/foods/**", "/api/orders/all","/api/orders/status/**").permitAll().anyRequest().authenticated())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
                return http.build();

    }
  @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    //cors filter
    @Bean
    public CorsFilter corsFilter(CorsConfigurationSource corsConfigurationSource){
        return new CorsFilter(corsConfigurationSource());

    }

    private UrlBasedCorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowedOrigins(List.of("https://cravecartjun.netlify.app/" , "https://craveacart-admin.netlify.app/" ,"http://localhost:5173"));
        corsConfiguration.setAllowedMethods(List.of("GET","POST","PUT","DELETE","OPTIONS", "PATCH"));
        corsConfiguration.setAllowedHeaders(List.of("Authorization" , "Content-Type"));
        corsConfiguration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);
        return source;
    }

    @Bean
    public AuthenticationManager authenticationManager(){
        DaoAuthenticationProvider daop = new DaoAuthenticationProvider(auth);
        daop.setPasswordEncoder(passwordEncoder());
        return new ProviderManager(daop);

    }

}
