package com.cherrysoft.manics.model.v2.specs;

import com.cherrysoft.manics.model.v2.Cartoon;
import com.cherrysoft.manics.model.v2.CartoonType;
import lombok.Data;

import java.util.Set;

import static java.util.Objects.isNull;

@Data
public abstract class CartoonSpec {
  protected final Cartoon cartoon;
  protected Set<Long> categoryIds;

  public CartoonSpec(Cartoon cartoon, CartoonType cartoonType, Set<Long> categoryIds) {
    this.cartoon = cartoon;
    this.cartoon.setType(cartoonType);
    this.categoryIds = categoryIds;
  }

  public Set<Long> getCategoryIds() {
    if (isNull(categoryIds)) {
      return Set.of();
    }
    return categoryIds;
  }

}
