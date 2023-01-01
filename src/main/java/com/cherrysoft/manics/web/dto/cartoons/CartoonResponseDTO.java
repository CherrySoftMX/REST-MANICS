package com.cherrysoft.manics.web.dto.cartoons;

import com.cherrysoft.manics.model.CartoonType;
import com.cherrysoft.manics.web.dto.validation.OnCreate;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.Year;

@Data
@EqualsAndHashCode(callSuper = true)
@Relation(collectionRelation = "cartoons", itemRelation = "cartoon")
public class CartoonResponseDTO extends RepresentationModel<CartoonResponseDTO> {
  @JsonProperty(access = JsonProperty.Access.READ_ONLY)
  private final Long id;

  @JsonProperty(access = JsonProperty.Access.READ_ONLY)
  private final CartoonType type;

  @NotBlank(
      message = "The name of the cartoon MUST be provided.",
      groups = OnCreate.class
  )
  private final String name;

  @NotBlank(
      message = "The author of the cartoon MUST be provided.",
      groups = OnCreate.class
  )
  private final String author;

  @NotNull(
      message = "Publication year of the cartoon MUST be provided.",
      groups = OnCreate.class
  )
  private final Year publicationYear;

  @NotNull(
      message = "Available chapters count MUST be provided.",
      groups = OnCreate.class
  )
  private final Integer availableChapters;
}
