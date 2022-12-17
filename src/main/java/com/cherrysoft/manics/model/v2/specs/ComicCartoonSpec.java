package com.cherrysoft.manics.model.v2.specs;

import com.cherrysoft.manics.model.v2.Cartoon;
import com.cherrysoft.manics.model.v2.CartoonType;

import java.util.Set;

public class ComicCartoonSpec extends CartoonSpec {

  public ComicCartoonSpec(Cartoon cartoon, Set<Long> categoryIds) {
    super(cartoon, CartoonType.COMIC, categoryIds);
  }

}
