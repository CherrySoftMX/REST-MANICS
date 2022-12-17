package com.cherrysoft.manics.controller.v2.dto.cartoons;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.Data;

@Data
public class CartoonResponseDTO {
  @JsonUnwrapped
  private final BaseCartoonFields baseCartoonFields;
}
