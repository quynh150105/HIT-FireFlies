package com.example.hit_networking_base.config;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;

import java.util.HashMap;
import java.util.Map;

public class DotenvApplicationContextInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load();
        Map<String, Object> envMap = new HashMap<>();

        dotenv.entries().forEach(entry -> {
            envMap.put(entry.getKey(), entry.getValue());
            System.setProperty(entry.getKey(), entry.getValue()); // vẫn giữ để log hoặc dùng System.getProperty()
        });

        ConfigurableEnvironment environment = applicationContext.getEnvironment();
        environment.getPropertySources().addFirst(new MapPropertySource("dotenv", envMap));

        System.out.println("✅ Loaded .env into Spring Environment [EARLY]");
    }
}
