package kz.unm.tusupkalimiraszhaugashnurzhan.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TusupkaliMirasZhaugashNurzhanOpenApiConfig {

    @Bean
    public OpenAPI tusupkaliMirasZhaugashNurzhanUniversityManagementOpenApi() {
        String bearerAuth = "bearerAuth";
        return new OpenAPI()
                .info(new Info()
                        .title("University Management System API")
                        .version("1.0.0")
                        .description("Spring Boot backend for managing students, teachers, courses, enrollments, "
                                + "departments, users, roles, file attachments, and async reports.")
                        .contact(new Contact()
                                .name("Tusupkali Miras and Zhaugash Nurzhan")))
                .addSecurityItem(new SecurityRequirement().addList(bearerAuth))
                .components(new Components()
                        .addSecuritySchemes(bearerAuth, new SecurityScheme()
                                .name(bearerAuth)
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")));
    }
}
