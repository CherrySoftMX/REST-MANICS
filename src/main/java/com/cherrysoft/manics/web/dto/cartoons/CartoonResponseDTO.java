package com.cherrysoft.manics.web.dto.cartoons;

import com.cherrysoft.manics.model.CartoonType;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Data
@EqualsAndHashCode(callSuper = true)
@Relation(collectionRelation = "cartoons", itemRelation = "cartoon")
public class CartoonResponseDTO extends RepresentationModel<CartoonResponseDTO> {
  @JsonProperty(access = JsonProperty.Access.READ_ONLY)
  private final Long id;

  @JsonProperty(access = JsonProperty.Access.READ_ONLY)
  private final CartoonType type;

  @JsonUnwrapped
  private final BaseCartoonFields baseCartoonFields;
}
