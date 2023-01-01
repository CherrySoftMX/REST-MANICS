package com.cherrysoft.manics.model;

import com.cherrysoft.manics.model.auth.ManicUser;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;
import org.hibernate.annotations.SortNatural;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.FullTextField;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.Indexed;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.IndexedEmbedded;

import javax.persistence.*;
import java.time.Year;
import java.util.*;

import static java.util.Objects.isNull;

@Getter
@Setter
@ToString
@Entity
@Table(name = "cartoons")
@Indexed(index = "cartoons")
public class Cartoon {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "cartoon_id")
  protected Long id;

  @Column
  @FullTextField
  private String name;

  @Enumerated(EnumType.ORDINAL)
  private CartoonType type;

  @Column
  @FullTextField
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
  @IndexedEmbedded
  private List<Chapter> chapters;

  @OneToMany(
      mappedBy = "cartoon",
      fetch = FetchType.LAZY,
      cascade = CascadeType.ALL,
      orphanRemoval = true
  )
  @ToString.Exclude
  private List<Comment> comments;

  @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
  @JoinTable(name = "cartoon_categories",
      joinColumns = @JoinColumn(name = "cartoon_id"),
      inverseJoinColumns = @JoinColumn(name = "category_id")
  )
  @ToString.Exclude
  @SortNatural
  private SortedSet<Category> categories;

  @ManyToMany(mappedBy = "likes")
  @ToString.Exclude
  private Set<ManicUser> likedBy;

  @ManyToMany(mappedBy = "bookmarks")
  @ToString.Exclude
  private Set<ManicUser> bookmarkedBy;

  public void setChapters(List<Chapter> chapters) {
    if (isNull(chapters)) {
      return;
    }
    chapters.forEach(c -> c.setCartoon(this));
    this.chapters = chapters;
  }

  public void setCategories(Set<Category> categories) {
    if (isNull(categories)) {
      return;
    }
    this.categories = new TreeSet<>(categories);
  }

  public void removeLikes() {
    for (Iterator<ManicUser> iterator = getLikedBy().iterator(); iterator.hasNext(); ) {
      ManicUser user = iterator.next();
      iterator.remove();
      user.getLikes().remove(this);
    }
  }

  public void removeBookmarks() {
    for (Iterator<ManicUser> iterator = getBookmarkedBy().iterator(); iterator.hasNext(); ) {
      ManicUser user = iterator.next();
      iterator.remove();
      user.getBookmarks().remove(this);
    }
  }

  public void updateCategories(Set<Category> categories) {
    this.categories.clear();
    this.categories.addAll(categories);
  }

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
