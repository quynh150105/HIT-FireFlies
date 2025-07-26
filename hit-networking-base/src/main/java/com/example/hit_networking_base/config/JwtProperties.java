package com.example.hit_networking_base.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "jwt")
@Data
public class JwtProperties {
    private String secret;
    private long accessExpirationTime;
    private long refreshExpirationTime;
    public long getAccessExpirationTime() {
        return this.accessExpirationTime * 60 * 1000;
    }

    public long getRefreshExpirationTime() {
        return this.refreshExpirationTime * 60 * 1000;
    }
}