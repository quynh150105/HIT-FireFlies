package com.example.hit_networking_base.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.util.Base64;


@Configuration
public class EnvironmentVariableConfig {
    @Value("${black.list}")
    private String blackList;

    public String getBlackList(){
        byte[] decodeBytes = Base64.getDecoder().decode(this.blackList);
        return new String(decodeBytes);
    }
}
