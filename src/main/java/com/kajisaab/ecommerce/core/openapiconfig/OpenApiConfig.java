package com.kajisaab.ecommerce.core.openapiconfig;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
        info = @Info(
                contact = @Contact(
                        name = "Aman",
                        email = "amankhadkakaji@gmail.com"
                ),
                description = "Open Api documentation for Spring Security",
                title = "OpenApi specification - Aman",
                version = "1.0",
                license = @License(
                        name = "License Name",
                        url = "https://some-url.com"
                ),
                termsOfService = "Terms of service"
        ),
        servers = {
                @Server(
                        description = "Local Dev Environment",
                        url = "http://localhost:8080"
                )
        },
        security = @SecurityRequirement(
                name = "X-XSRF-TOKEN"
        )
)
@SecurityScheme(
        name = "X-XSRF-TOKEN",
        description = "JWT auth description",
        type = SecuritySchemeType.APIKEY,
        in = SecuritySchemeIn.HEADER
)
public class OpenApiConfig {
}
