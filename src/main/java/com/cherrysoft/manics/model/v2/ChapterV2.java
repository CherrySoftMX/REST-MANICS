package com.cherrysoft.manics.model.v2;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import static java.util.Objects.isNull;

@Setter
@Getter
@ToString
@Entity
@Table(name = "chapters_v2")
public class ChapterV2 {
  public static final String MONTH_DAY_YEAR_PATTERN = "MM-dd-yyyy";

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "chapter_id")
  private Integer id;

  @Column
  private Integer chapterNumber;

  @Column
  private String name;

  @Column
  private LocalDate publicationDate;

  @Column
  private Integer totalPages;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "cartoon_id")
  @ToString.Exclude
  private Cartoon cartoon;

  @OneToMany(
      mappedBy = "chapter",
      cascade = CascadeType.ALL,
      fetch = FetchType.LAZY
  )
  @ToString.Exclude
  private List<PageV2> pages;

  public void setPages(List<PageV2> pages) {
    if (isNull(pages)) {
      return;
    }
    pages.forEach(p -> p.setChapter(this));
    this.pages = pages;
  }

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
