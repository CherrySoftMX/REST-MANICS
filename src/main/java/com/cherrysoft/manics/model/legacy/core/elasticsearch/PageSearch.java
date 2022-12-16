package com.cherrysoft.manics.model.legacy.core.elasticsearch;

import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import javax.persistence.Id;
import java.util.Arrays;

public class PageSearch {
  @Id
  private Integer id;

  @Field(name = "vector_values", type = FieldType.Dense_Vector, dims = 20)
  private Double[] vector;

  public Integer getPageId() {
    return id;
  }

  public void setPageId(Integer pageId) {
    this.id = pageId;
  }

  public Double[] getVector() {
    return vector;
  }

  public void setVector(Double[] vector) {
    this.vector = vector;
  }

  @Override
  public String toString() {
    return "PageSearch [pageId=" + id + "vector=" + Arrays.toString(vector) + "]";
  }

}
