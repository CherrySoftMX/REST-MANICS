package com.cherrysoft.manics.model.v2;

import com.cherrysoft.manics.model.v2.auth.ManicUser;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Calendar;

@Getter
@Entity
@Table(name = "suggestions_v2")
public class SuggestionV2 {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "suggestion_id")
  private Integer id;

  @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
  @JoinColumn(name = "user_id", nullable = false)
  private ManicUser user;

  @Column
  private String content;

  @Column(nullable = false, updatable = false)
  @CreationTimestamp
  @Temporal(TemporalType.TIMESTAMP)
  private Calendar creationDate;
}
