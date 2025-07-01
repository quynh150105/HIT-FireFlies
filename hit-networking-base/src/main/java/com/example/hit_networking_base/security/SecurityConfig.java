package com.example.hit_networking_base.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable() // tắt CSRF cho đơn giản (nếu không dùng cookie)
                .authorizeRequests()
                .antMatchers("/api/jobposts/**").permitAll() // cho phép public
                .anyRequest().authenticated(); // các request khác cần auth
    }
}
