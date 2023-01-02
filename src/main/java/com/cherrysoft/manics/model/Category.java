package com.cherrysoft.manics.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;
import org.hibernate.search.engine.backend.types.Sortable;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.FullTextField;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.Indexed;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.KeywordField;

import javax.persistence.*;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@ToString
@Entity
@Table(name = "categories")
@Indexed(index = "categories")
public class Category implements Comparable<Category> {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "category_id")
  private Long id;

  @Column(nullable = false)
  @KeywordField(sortable = Sortable.YES)
  private String name;

  @Column(nullable = false)
  @FullTextField
  private String description;

  @ManyToMany(mappedBy = "categories")
  @ToString.Exclude
  private Set<Cartoon> cartoons;

  public void removeCartoons() {
    for (Iterator<Cartoon> iterator = getCartoons().iterator(); iterator.hasNext(); ) {
      Cartoon cartoon = iterator.next();
      iterator.remove();
      cartoon.getCategories().remove(this);
    }
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
      return false;
    }
    Category that = (Category) o;
    return id != null && Objects.equals(id, that.id);
  }

  @Override
  public int hashCode() {
    return getClass().hashCode();
  }

  @Override
  public int compareTo(Category o) {
    return o.getName().compareTo(this.getName());
  }

}
