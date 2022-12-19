package com.cherrysoft.manics.web.v2.dto;

import com.cherrysoft.manics.web.v2.dto.validation.OnCreate;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class CategoryDTO {
  @JsonProperty(access = JsonProperty.Access.READ_ONLY)
  private final Long id;

  @Size(
      min = 5,
      max = 50,
      message = "Name name must be between {min} and {max} chars.",
      groups = OnCreate.class
  )
  @NotBlank(
      message = "Category name MUST NOT be empty.",
      groups = OnCreate.class
  )
  private final String name;

  @NotBlank(
      message = "Category description MUST NOT be empty.",
      groups = OnCreate.class
  )
  private final String description;
}
