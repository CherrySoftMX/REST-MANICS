package com.cherrysoft.manics.model.v2.specs;

import com.cherrysoft.manics.model.v2.CommentV2;
import lombok.Data;

@Data
public class CreateCommentSpec {
  private final Long cartoonId;
  private final Long userId;
  private final CommentV2 newComment;
}
