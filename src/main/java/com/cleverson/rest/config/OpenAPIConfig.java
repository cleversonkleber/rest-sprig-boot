package com.cleverson.rest.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@Configuration
public class OpenAPIConfig {
    
    @Bean
    OpenAPI customOpenAPI(){
        return new OpenAPI()
                .info(new Info()
                    .title("Hello Swagger OpenAPI")
                    .version("v1")
                    .description("Some description about your API.")
                    .termsOfService("")
                    .license(
                        new License()
                            .name("Apache 2.0")
                            .url("")  
                    ));
    }
}
