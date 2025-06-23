package com.example.hit_networking_base.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    private final String[] SWAGGER_WHITELIST =  {
            "/swagger-ui.html",
            "/swagger-ui/**",
            "/v3/api-docs/**",
            "/swagger-resources/**",
            "/webjars/**"
    };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
        httpSecurity.csrf(csrf -> csrf.disable())
                    .sessionManagement(
                            session -> session
                            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                    )
                    .authorizeRequests()
                        .antMatchers(SWAGGER_WHITELIST).permitAll()
                        .antMatchers("/api/v1/auth/**").permitAll()
                        .anyRequest().authenticated();
        return httpSecurity.build();
    }
}


//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
//        httpSecurity
//                .csrf(csrf -> csrf.disable())
//                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//                .authorizeHttpRequests(auth -> auth
//                        .antMatchers(SWAGGER_WHITELIST).permitAll()
//                        .anyRequest().permitAll()
//                );
//        return httpSecurity.build();
//    }
//}
