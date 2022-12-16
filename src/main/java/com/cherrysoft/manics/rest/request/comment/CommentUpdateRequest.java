package com.cherrysoft.manics.rest.request.comment;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class CommentUpdateRequest {

  @NotNull(message = "Se requiere el contenido del comentario.")
  @NotEmpty(message = "El contenido del  comentario no puede estar vacío.")
  private String content;

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

}
