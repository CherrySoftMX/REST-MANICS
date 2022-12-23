package com.cherrysoft.manics.model.v2.search;

import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import javax.persistence.Id;

@Data
@Document(indexName = "cartoons")
public class SearchingCartoon {
  @Id
  private Long id;

  @Field(type = FieldType.Text)
  private String name;
}
