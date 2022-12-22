package com.cherrysoft.manics.model.v2;

import com.cherrysoft.manics.model.v2.auth.ManicUser;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Objects;

@Getter
@Setter
@Entity
@Table(name = "suggestions_v2")
public class SuggestionV2 {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "suggestion_id")
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
  @JoinColumn(name = "user_id", nullable = false)
  private ManicUser user;

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
    SuggestionV2 that = (SuggestionV2) o;
    return id != null && Objects.equals(id, that.id);
  }

  @Override
  public int hashCode() {
    return getClass().hashCode();
  }

}
