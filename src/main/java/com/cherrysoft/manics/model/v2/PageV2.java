package com.cherrysoft.manics.model.v2;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;

@Setter
@Getter
@ToString
@Entity
@Table(name = "pages_v2")
public class PageV2 {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "page_id")
  private Long id;

  @Column
  private Integer pageNumber;

  @Column
  private String imageUrl;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "chapter_id")
  @ToString.Exclude
  private ChapterV2 chapter;

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
      return false;
    }
    PageV2 pageV2 = (PageV2) o;
    return id != null && Objects.equals(id, pageV2.id);
  }

  @Override
  public int hashCode() {
    return getClass().hashCode();
  }

}
