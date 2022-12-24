package com.cherrysoft.manics.model.specs;

import com.cherrysoft.manics.model.Comment;
import lombok.Data;

@Data
public class CreateCommentSpec {
  private final Long cartoonId;
  private final Long userId;
  private final Comment newComment;
}
