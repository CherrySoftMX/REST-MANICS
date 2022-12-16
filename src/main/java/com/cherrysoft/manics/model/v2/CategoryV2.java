package com.cherrysoft.manics.model.v2;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@ToString
@Entity
@Table(name = "categories_v2")
public class CategoryV2 implements Comparable<CategoryV2> {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "category_id")
  private Long id;

  @Column(nullable = false)
  private String name;

  @Column(nullable = false)
  private String description;

  @ManyToMany(mappedBy = "categories")
  @ToString.Exclude
  private Set<Cartoon> cartoons;

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

  @Override
  public int compareTo(CategoryV2 o) {
    return o.getName().compareTo(this.getName());
  }

}
