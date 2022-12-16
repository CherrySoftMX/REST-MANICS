package com.cherrysoft.manics.model.v2.auth;

import com.cherrysoft.manics.model.v2.Cartoon;
import com.cherrysoft.manics.model.v2.SuggestionV2;
import lombok.Getter;
import lombok.ToString;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Getter
@ToString
@Entity
@Table(name = "manic_users")
public class ManicUser {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "user_id")
  private Long id;

  @Column
  private String username;

  @Column
  private String email;

  @Column
  private String password;

  @ElementCollection(fetch = FetchType.EAGER)
  private Set<UserRole> roles = Set.of(UserRole.NORMAL);

  @OneToMany(
      mappedBy = "user",
      fetch = FetchType.LAZY,
      orphanRemoval = true
  )
  @ToString.Exclude
  private List<SuggestionV2> suggestions;

  @ManyToMany(cascade = CascadeType.ALL)
  @JoinTable(
      name = "likes",
      joinColumns = {@JoinColumn(name = "user_id")},
      inverseJoinColumns = {@JoinColumn(name = "cartoon_id")}
  )
  @ToString.Exclude
  private Set<Cartoon> likes = new HashSet<>();

  @ManyToMany(cascade = CascadeType.ALL)
  @JoinTable(
      name = "read_later",
      joinColumns = {@JoinColumn(name = "user_id")},
      inverseJoinColumns = {@JoinColumn(name = "cartoon_id")}
  )
  @ToString.Exclude
  private Set<Cartoon> readLater = new HashSet<>();

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
      return false;
    }
    ManicUser manicUser = (ManicUser) o;
    return id != null && Objects.equals(id, manicUser.id);
  }

  @Override
  public int hashCode() {
    return getClass().hashCode();
  }

}
