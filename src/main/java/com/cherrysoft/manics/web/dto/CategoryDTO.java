package com.cherrysoft.manics.web.dto;

import com.cherrysoft.manics.web.dto.validation.OnCreate;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@EqualsAndHashCode(callSuper = true)
@Relation(collectionRelation = "categories", itemRelation = "category")
public class CategoryDTO extends RepresentationModel<CategoryDTO> {
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
