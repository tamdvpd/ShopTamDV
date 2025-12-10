package com.tamdvshop.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerOpenApiConfig {

    @Bean
    public OpenAPI shopOpenAPI() {
        final String scheme = "bearerAuth";
        return new OpenAPI()
                .info(new Info().title("TamDV Shop API").version("v2"))
                .addSecurityItem(new SecurityRequirement().addList(scheme))
                .components(new Components().addSecuritySchemes(scheme,
                        new SecurityScheme()
                                .name(scheme)
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")));
    }

    @Bean
    public GroupedOpenApi tamdvGroup() {
        return GroupedOpenApi.builder()
                .group("tamdvshop")
                .packagesToScan("com.tamdvshop")
                .build();
    }
}
