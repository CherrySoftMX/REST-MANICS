package com.cherrysoft.manics.model.v2;

import lombok.Getter;
import lombok.ToString;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;

@Getter
@ToString
@Entity
@Table(name = "categories_v2")
public class CategoryV2 {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "category_id")
  private Long id;

  @Column
  private String name;

  @Column
  private String description;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "cartoon_id")
  @ToString.Exclude
  private Cartoon cartoon;

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
      return false;
    }
    CategoryV2 that = (CategoryV2) o;
    return id != null && Objects.equals(id, that.id);
  }

  @Override
  public int hashCode() {
    return getClass().hashCode();
  }

}
