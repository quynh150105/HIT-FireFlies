package com.example.hit_networking_base.security;

import lombok.RequiredArgsConstructor;
import org.springframework.web.filter.CorsFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final String[] SWAGGER_WHITELIST =  {
            "/swagger-ui.html",
            "/swagger-ui/**",
            "/v3/api-docs/**",
            "/swagger-resources/**",
            "/webjars/**",
            "/api/v1/auth/**",
            "/auth/set-front"
    };

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final String ADMIN = "/api/v1/admin/**";
    private final String USER = "/api/v1/users/**";


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
        httpSecurity.cors().and()
                .csrf(csrf -> csrf.disable())
                .sessionManagement(
                        session -> session
                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authorizeRequests()
                .antMatchers(SWAGGER_WHITELIST).permitAll()
                .antMatchers(ADMIN).hasAnyRole("ADMIN", "BQT")
                .antMatchers(USER).hasAnyRole("USER", "ADMIN", "BQT", "TV")
                .anyRequest().authenticated()
                .and()
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return httpSecurity.build();
    }


    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowCredentials(true);

        corsConfiguration.setAllowedOrigins(List.of(
                "http://127.0.0.1:5500",
                "https://hit-network-system.onrender.com",
                "http://localhost:5174",
                "https://hitnetwork.onrender.com"
        ));

        corsConfiguration.setAllowedHeaders(List.of("Origin", "Content-Type", "Accept", "Authorization"));
        corsConfiguration.setExposedHeaders(List.of("Authorization"));
        corsConfiguration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);

        return new CorsFilter(source);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }
}
