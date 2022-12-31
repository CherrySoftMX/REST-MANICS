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
import java.util.Objects;

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

  @Column
  @FullTextField
  private String content;

  @Column(nullable = false, updatable = false)
  @CreationTimestamp
  @Temporal(TemporalType.TIMESTAMP)
  private Calendar createdAt;

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
