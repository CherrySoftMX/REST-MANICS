package com.cherrysoft.manics.model.specs;

import com.cherrysoft.manics.model.Cartoon;
import com.cherrysoft.manics.model.CartoonType;

import java.util.Set;

public class MangaCartoonSpec extends CartoonSpec {

  public MangaCartoonSpec(Cartoon cartoon, Set<Long> categoryIds) {
    super(cartoon, CartoonType.MANGA, categoryIds);
  }

}
