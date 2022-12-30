package com.cherrysoft.manics.config;

import com.cherrysoft.manics.exception.ErrorResponse;
import io.swagger.v3.core.converter.AnnotatedType;
import io.swagger.v3.core.converter.ModelConverters;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.customizers.OperationCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.Optional;

import static com.cherrysoft.manics.util.ApiDocsConstants.*;
import static java.util.Objects.nonNull;

@Configuration
public class SwaggerConfig {

  @Bean
  public OpenAPI openAPI() {
    final License license = new License()
        .name("Licensed under GNU GENERAL PUBLIC LICENSE, VERSION 3.0")
        .url("https://www.gnu.org/licenses/gpl-3.0.en.html");
    final Contact contact = new Contact()
        .name("CherrySoft team")
        .url("https://www.cherrysoft.com")
        .email("contact@cherrysoft.com");
    final Info info = new Info()
        .title("Comics and Mangas API")
        .description("DISCLAIMER: " +
            "Data security is not guaranteed. " +
            "When using the application, do not enter any real personal information. " +
            "You enter any personal information at your own risk.")
        .version("v1")
        .license(license)
        .contact(contact);
    final SecurityScheme securityScheme = new SecurityScheme()
        .type(SecurityScheme.Type.HTTP)
        .in(SecurityScheme.In.HEADER)
        .scheme("bearer")
        .bearerFormat("JWT")
        .name("Authorization");
    final Components components = new Components()
        .addSecuritySchemes("JWT Token", securityScheme)
        .addResponses(BAD_REQUEST_RESPONSE_REF, errorResponse("Bad request"))
        .addResponses(NOT_FOUND_RESPONSE_REF, errorResponse("Resource not found"))
        .addResponses(UNAUTHORIZED_RESPONSE_REF, unauthorizedResponse())
        .addResponses(FORBIDDEN_RESPONSE_REF, forbiddenResponse());
    return new OpenAPI()
        .info(info)
        .components(components)
        .addSecurityItem(new SecurityRequirement().addList("JWT Token"));
  }

  private ApiResponse errorResponse(String description) {
    Schema errorResponseSchema = ModelConverters.getInstance()
        .resolveAsResolvedSchema(new AnnotatedType(ErrorResponse.class)).schema;

    return new ApiResponse().content(
            new Content()
                .addMediaType("application/json", new MediaType().schema(errorResponseSchema)))
        .description(description);
  }

  private ApiResponse unauthorizedResponse() {
    return new ApiResponse().content(new Content()
            .addMediaType("application/json", new MediaType()))
        .description("Authorization information is missing or invalid");
  }

  private ApiResponse forbiddenResponse() {
    return new ApiResponse().content(new Content()
            .addMediaType("application/json", new MediaType()))
        .description("The user doesn't have permission to access this resource");
  }

  @Bean
  public OperationCustomizer operationCustomizer() {
    return (operation, handlerMethod) -> {
      Optional<PreAuthorize> preAuthorizeAnnotation = Optional.ofNullable(handlerMethod.getMethodAnnotation(PreAuthorize.class));
      StringBuilder sb = new StringBuilder();
      if (preAuthorizeAnnotation.isPresent()) {
        sb.append("This api requires **")
            .append((preAuthorizeAnnotation.get()).value().replaceAll("hasRole|\\(|\\)|\\'", ""))
            .append("** permission.");
      } else {
        sb.append("This api is **public**");
      }
      sb.append("<br /><br />");
      if (nonNull(operation.getDescription())) {
        sb.append(operation.getDescription());
      }
      operation.setDescription(sb.toString());
      return operation;
    };
  }

}