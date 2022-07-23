package com.manics.rest.model.core.elasticsearch;

import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import javax.persistence.Id;
import java.util.List;

@Document(indexName = "stories")
public class StorySearch {
  @Id
  private Integer id;

  @Field(name = "name", type = FieldType.Text)
  private String name;

  @Field(type = FieldType.Nested)
  private List<PageSearch> pages;

  public Integer getStoryId() {
    return id;
  }

  public void setStoryId(Integer id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public List<PageSearch> getPages() {
    return pages;
  }

  public void setPages(List<PageSearch> pages) {
    this.pages = pages;
  }

}
