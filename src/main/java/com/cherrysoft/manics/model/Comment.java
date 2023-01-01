package com.cherrysoft.manics.model;

import com.cherrysoft.manics.model.auth.ManicUser;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.FullTextField;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.Indexed;

import javax.persistence.*;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

import static java.util.Objects.nonNull;

@Getter
@Setter
@ToString
@Entity
@Table(name = "comments")
@Indexed(index = "comments")
public class Comment {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "comment_id")
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", nullable = false)
  @ToString.Exclude
  private ManicUser user;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "cartoon_id", nullable = false)
  @ToString.Exclude
  private Cartoon cartoon;

  @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JoinColumn(name = "parent_id", referencedColumnName = "comment_id")
  @ToString.Exclude
  private Comment parent;

  @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL)
  @ToString.Exclude
  private List<Comment> comments;

  @Column
  @FullTextField
  private String content;

  @Column(nullable = false, updatable = false)
  @CreationTimestamp
  @Temporal(TemporalType.TIMESTAMP)
  private Calendar createdAt;

  public boolean hasParent() {
    return nonNull(parent);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
      return false;
    }
    Comment comment = (Comment) o;
    return id != null && Objects.equals(id, comment.id);
  }

  @Override
  public int hashCode() {
    return getClass().hashCode();
  }

}
