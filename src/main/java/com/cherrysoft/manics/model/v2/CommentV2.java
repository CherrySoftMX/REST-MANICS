package com.cherrysoft.manics.model.v2;

import com.cherrysoft.manics.model.v2.auth.ManicUser;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Objects;

@Getter
@Setter
@ToString
@Entity
@Table(name = "comments_v2")
public class CommentV2 {
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
    CommentV2 commentV2 = (CommentV2) o;
    return id != null && Objects.equals(id, commentV2.id);
  }

  @Override
  public int hashCode() {
    return getClass().hashCode();
  }

}
