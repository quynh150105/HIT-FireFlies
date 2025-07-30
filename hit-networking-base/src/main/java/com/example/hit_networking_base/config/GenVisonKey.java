package com.example.hit_networking_base.config;

import com.google.api.gax.core.FixedCredentialsProvider;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.vision.v1.ImageAnnotatorClient;
import com.google.cloud.vision.v1.ImageAnnotatorSettings;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;


import javax.annotation.PostConstruct;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Base64;

@Configuration
public class GenVisonKey {

    @PostConstruct
    public void initVisionKey() throws IOException {
        String base64Key = System.getenv("VISION_KEY_BASE64");
        if (base64Key != null && !base64Key.isEmpty()) {
            Path filePath = Paths.get("src/main/resources/secrets/vision-key.json");
            Files.createDirectories(filePath.getParent());
            Files.write(filePath, Base64.getDecoder().decode(base64Key),
                    StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
            System.setProperty("GOOGLE_APPLICATION_CREDENTIALS", filePath.toAbsolutePath().toString());
            System.out.println("File created at: " + filePath.toAbsolutePath());
            System.out.println("GOOGLE_APPLICATION_CREDENTIALS: " + System.getProperty("GOOGLE_APPLICATION_CREDENTIALS"));
        }
    }
    @Bean
    public ImageAnnotatorClient imageAnnotatorClient() throws IOException {
        String path = System.getProperty("GOOGLE_APPLICATION_CREDENTIALS");
        GoogleCredentials credentials = GoogleCredentials.fromStream(new FileInputStream(path));
        ImageAnnotatorSettings settings = ImageAnnotatorSettings.newBuilder()
                .setCredentialsProvider(FixedCredentialsProvider.create(credentials))
                .build();
        return ImageAnnotatorClient.create(settings);
    }
}
