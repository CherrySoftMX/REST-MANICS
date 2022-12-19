package com.cherrysoft.manics.web.legacy.request;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class CategoryRequest {

  @NotNull(message = "Se necesita un nombre para la categoría.")
  @NotEmpty(message = "El nombre de la categoría no puede estar vacío.")
  private String name;

  @NotNull(message = "Se necesita una descripción para la categoría.")
  @NotEmpty(message = "La descripción de la categoría no puede estar vacía.")
  private String description;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

}
