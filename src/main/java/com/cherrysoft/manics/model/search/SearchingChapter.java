package com.cherrysoft.manics.model.search;

import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import javax.persistence.Id;

@Data
@Document(indexName = "chapters")
public class SearchingChapter {
  @Id
  private Long id;

  @Field(type = FieldType.Text)
  private String name;
}
