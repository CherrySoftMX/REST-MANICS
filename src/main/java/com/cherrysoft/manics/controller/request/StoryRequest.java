package com.cherrysoft.manics.controller.request;

import com.cherrysoft.manics.controller.request.chapter.ChapterRequest;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

public class StoryRequest {

  @NotNull(message = "El nuevo relato necesita una categoría.")
  private Integer categoryId;

  @NotNull(message = "Se necesita el nombre del relato para el registro.")
  @NotEmpty(message = "El nombre del manga no puede estar vacío.")
  private String name;

  @NotNull(message = "Se necesita un autor para el registro del relato.")
  @NotEmpty(message = "El nombre del autor no puede estar vacío.")
  private String author;

  @NotNull(message = "Se necesita el año de publicación del relato.")
  private Integer publicationYear;

  @NotNull(message = "Los capítulos disponibles son obligatorios.")
  private Integer availableChapters;

  private List<ChapterRequest> chapters;

  public Integer getPublicationYear() {
    return publicationYear;
  }

  public void setPublicationYear(Integer publicationYear) {
    this.publicationYear = publicationYear;
  }

  public String getAuthor() {
    return author;
  }

  public void setAuthor(String author) {
    this.author = author;
  }

  public Integer getAvailableChapters() {
    return availableChapters;
  }

  public void setAvailableChapters(Integer availableChapters) {
    this.availableChapters = availableChapters;
  }

  public Integer getCategoryId() {
    return categoryId;
  }

  public void setCategoryId(Integer categoryId) {
    this.categoryId = categoryId;
  }

  public List<ChapterRequest> getChapters() {
    return chapters;
  }

  public void setChapters(List<ChapterRequest> chapters) {
    this.chapters = chapters;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
