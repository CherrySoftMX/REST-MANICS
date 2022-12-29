package com.cherrysoft.manics.model.specs;

import lombok.Data;

@Data
public class LikeSpec {
  private final Long cartoonId;
  private final Long userId;
}
