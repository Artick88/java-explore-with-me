package ru.practicum.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(info = @Info(title = "Main service API", version = "v1", contact = @Contact(name = "Патраков Артем")),
        servers = {@Server(url = "http://localhost:8080")})
public class OpenApiConfig {
}