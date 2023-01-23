package com.basilisk;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfiguration {
    @Bean
    public OpenAPI basiliskOpenApi(){


        SecurityRequirement requirement = new SecurityRequirement().addList("bearerAuth");
        SecurityScheme scheme = new SecurityScheme()
                .name("bearerAuth")
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT");

        Components jwtComponents = new Components().addSecuritySchemes("bearerAuth", scheme);

        Info info = new Info()
                .title("Basilisk API Documentation")
                .description("Halaman Documentation Aplikasi Basilisk")
                .version("v.1.0.0");
        var openAPI = new OpenAPI().info(info).addSecurityItem(requirement).components(jwtComponents);

        return openAPI;
    }
}
