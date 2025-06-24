package com.example.hit_networking_base.config;

import com.example.hit_networking_base.security.JwtAuthenticationFilter;
import com.example.hit_networking_base.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final String[] SWAGGER_WHITELIST =  {
            "/swagger-ui.html",
            "/swagger-ui/**",
            "/v3/api-docs/**",
            "/swagger-resources/**",
            "/webjars/**",
            "/api/v1/auth/**"
    };

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final String ADMIN = "/api/v1/admin/**";
    private final String USER = "/api/v1/users/**";


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
        httpSecurity.csrf(csrf -> csrf.disable())
                    .sessionManagement(
                            session -> session
                            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                    )
                    .authorizeRequests()
                        .antMatchers(SWAGGER_WHITELIST).permitAll()
                        .antMatchers(ADMIN).hasAnyRole("ADMIN", "TV")
                        .antMatchers(USER).hasAnyRole("USER", "ADMIN", "BQT")
                        .anyRequest().authenticated()
                    .and()
                    .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return httpSecurity.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(10);
    }

}
