package com.cherrysoft.manics.model.v2.search;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import javax.persistence.Id;

@Data
@Document(indexName = "pages")
@JsonIgnoreProperties(ignoreUnknown = true)
public class SearchingPage {
  @Id
  private Long id;

  @Field(name = "vector_values", type = FieldType.Dense_Vector, dims = 20)
  private Double[] imgVector;
}
