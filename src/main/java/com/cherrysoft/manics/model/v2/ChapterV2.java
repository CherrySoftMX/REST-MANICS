package com.cherrysoft.manics.model.v2;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Setter
@Getter
@ToString
@Entity
@Table(name = "chapters_v2")
public abstract class ChapterV2 {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "chapter_id")
  private Integer id;

  @Column
  private Integer chapterNumber;

  @Column
  private String name;

  @Column
  private String publicationDate;

  @Column
  private Integer totalPages;

  @ManyToOne(fetch = FetchType.LAZY)
  @ToString.Exclude
  private Cartoon cartoon;

  @OneToMany(
      mappedBy = "chapter",
      cascade = CascadeType.ALL,
      fetch = FetchType.LAZY
  )
  @ToString.Exclude
  private List<PageV2> page;

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
      return false;
    }
    ChapterV2 chapterV2 = (ChapterV2) o;
    return id != null && Objects.equals(id, chapterV2.id);
  }

  @Override
  public int hashCode() {
    return getClass().hashCode();
  }

}
