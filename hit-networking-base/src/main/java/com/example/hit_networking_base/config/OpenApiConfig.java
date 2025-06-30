package com.example.hit_networking_base.config;

import io.swagger.v3.core.converter.ModelConverters;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class OpenApiConfig {

    private final String API_KEY = "Bearer Token";

    // Inject MultipartFileArrayConverter
    private final MultipartFileArrayConverter multipartFileArrayConverter;

    @Bean
    public OpenAPI customOpenAPI() {

        ModelConverters.getInstance().addConverter(multipartFileArrayConverter);

        return new OpenAPI()
                .info(new Info()
                        .title("Hit Networking API")
                        .version("1.0")
                        .description("Documentation Hit Networking API v1.0"))
                .components(new Components().addSecuritySchemes(
                        API_KEY,
                        new SecurityScheme()
                                .name("AuthorizationController")
                                .scheme("bearer")
                                .bearerFormat("JWT")
                                .type(SecurityScheme.Type.HTTP)
                                .in(SecurityScheme.In.HEADER)
                ))
                .addSecurityItem(new SecurityRequirement().addList(API_KEY));
    }
}
