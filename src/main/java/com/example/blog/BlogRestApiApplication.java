package com.example.blog;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@OpenAPIDefinition(
    info =
        @Info(
            title = "Spring Boot Blog App REST APIs.",
            description = "Spring Boot Blog App Documentation.",
            version = "v1.0",
            contact = @Contact(name = "Arman", email = "Arman.Iqbal@yh.nackademin.se"),
            license = @License(name = "Apache 2.0")),
    externalDocs =
        @ExternalDocumentation(
            description = "Spring Boot Blog Application Documentation.",
            url = "https://github.com/Kotik112/blog-rest-api"))
public class BlogRestApiApplication {

  @Bean
  public ModelMapper modelMapper() {
    return new ModelMapper();
  }

  public static void main(String[] args) {
    SpringApplication.run(BlogRestApiApplication.class, args);
  }
}
