package com.cherrysoft.manics.model.v2.specs;

import lombok.Data;

@Data
public class LikeSpec {
  private final Long cartoonId;
  private final Long userId;
}
