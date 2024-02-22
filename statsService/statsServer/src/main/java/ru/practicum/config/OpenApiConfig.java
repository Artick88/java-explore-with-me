package ru.practicum.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(info = @Info(title = "Stat service API", version = "v1", contact = @Contact(name = "Патраков Артем")),
        servers = {@Server(url = "http://localhost:9090")})
public class OpenApiConfig {
}