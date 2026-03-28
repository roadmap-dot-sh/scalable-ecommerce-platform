package com.example.productservice.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI productOpenApi() {
        return new OpenAPI()
                .info(new Info()
                        .title("Product Catalog API")
                        .description("Product, category, inventory, search")
                        .version("v1"));
    }
}
