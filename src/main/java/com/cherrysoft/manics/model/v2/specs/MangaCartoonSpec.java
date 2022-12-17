package com.cherrysoft.manics.model.v2.specs;

import com.cherrysoft.manics.model.v2.Cartoon;
import com.cherrysoft.manics.model.v2.CartoonType;

import java.util.Set;

public class MangaCartoonSpec extends CartoonSpec {

  public MangaCartoonSpec(Cartoon cartoon, Set<Long> categoryIds) {
    super(cartoon, CartoonType.MANGA, categoryIds);
  }

}
