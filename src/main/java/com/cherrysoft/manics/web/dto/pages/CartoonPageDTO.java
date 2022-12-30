package com.cherrysoft.manics.web.dto.pages;

import com.cherrysoft.manics.web.dto.validation.OnCreate;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@EqualsAndHashCode(callSuper = true)
@Relation(collectionRelation = "pages", itemRelation = "page")
public class CartoonPageDTO extends RepresentationModel<CartoonPageDTO> {
  @JsonProperty(access = JsonProperty.Access.READ_ONLY)
  private final Long id;

  @NotNull(
      message = "The page number for this page MUST be provided.",
      groups = OnCreate.class
  )
  private final Integer pageNumber;

  @NotBlank(
      message = "The image URL of the page MUST be provided.",
      groups = OnCreate.class
  )
  private final String imageUrl;
}
