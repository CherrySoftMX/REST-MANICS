package com.manics.rest.model.core;

import javax.persistence.*;

@Entity
@Table(name = "categories")
public class Category {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "category_id")
  private Integer id;

  @Column
  private String name;

  @Column
  private String description;

  public Integer getId() {
    return id;
  }

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

  @Override
  public String toString() {
    return "Categoria [description=" + description + ", id=" + id + ", name=" + name + "]";
  }

}
