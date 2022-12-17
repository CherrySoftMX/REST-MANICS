package com.cherrysoft.manics.model.v2;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;
import org.hibernate.annotations.SortNatural;

import javax.persistence.*;
import java.time.Year;
import java.util.*;

import static java.util.Objects.isNull;

@Getter
@Setter
@ToString
@Entity
@Table(name = "cartoons")
public class Cartoon {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "cartoon_id")
  protected Long id;

  @Column
  private String name;

  @Enumerated(EnumType.ORDINAL)
  private CartoonType type;

  @Column
  private String author;

  @Column
  private Integer availableChapters;

  @Column
  private Year publicationYear;

  @OneToMany(
      mappedBy = "cartoon",
      fetch = FetchType.LAZY,
      cascade = CascadeType.ALL,
      orphanRemoval = true
  )
  @ToString.Exclude
  private List<ChapterV2> chapters;

  @OneToMany(
      mappedBy = "cartoon",
      fetch = FetchType.LAZY,
      cascade = CascadeType.ALL,
      orphanRemoval = true
  )
  @ToString.Exclude
  private List<CommentV2> comments;

  @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
  @JoinTable(name = "cartoon_categories",
      joinColumns = @JoinColumn(name = "cartoon_id"),
      inverseJoinColumns = @JoinColumn(name = "category_id")
  )
  @ToString.Exclude
  @SortNatural
  private SortedSet<CategoryV2> categories;

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
      return false;
    }
    Cartoon cartoon = (Cartoon) o;
    return id != null && Objects.equals(id, cartoon.id);
  }

  @Override
  public int hashCode() {
    return getClass().hashCode();
  }

}
