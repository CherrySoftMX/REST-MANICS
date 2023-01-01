package com.cherrysoft.manics.model.specs;

import com.cherrysoft.manics.model.Comment;
import lombok.Data;

import static java.util.Objects.nonNull;

@Data
public class CreateCommentSpec {
  private final Long cartoonId;
  private final Long userId;
  private final Long parentId;
  private final Comment newComment;

  public boolean parentIdProvided() {
    return nonNull(parentId);
  }

}
