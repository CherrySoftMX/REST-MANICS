package com.cherrysoft.manics.model;

import java.util.stream.Stream;

public enum CartoonType {
  COMIC,
  MANGA;

  public static CartoonType of(String type) {
    return Stream.of(CartoonType.values())
        .filter(t -> t.name().equalsIgnoreCase(type))
        .findFirst()
        .orElseThrow();
  }

}
