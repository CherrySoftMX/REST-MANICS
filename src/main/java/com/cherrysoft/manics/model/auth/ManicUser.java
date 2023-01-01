package com.cherrysoft.manics.model.auth;

import com.cherrysoft.manics.model.Cartoon;
import com.cherrysoft.manics.model.Suggestion;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(
    name = "manic_users",
    uniqueConstraints = @UniqueConstraint(columnNames = {"username", "email"})
)
public class ManicUser {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "user_id")
  private Long id;

  @Column(nullable = false)
  private String username;

  @Column(nullable = false)
  private String email;

  @Column(nullable = false)
  private String password;

  @ElementCollection(fetch = FetchType.EAGER)
  private Set<ManicUserRole> roles;

  @OneToMany(
      mappedBy = "user",
      fetch = FetchType.LAZY,
      orphanRemoval = true
  )
  @ToString.Exclude
  private List<Suggestion> suggestions;

  @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
  @JoinTable(
      name = "likes",
      joinColumns = {@JoinColumn(name = "user_id")},
      inverseJoinColumns = {@JoinColumn(name = "cartoon_id")}
  )
  @ToString.Exclude
  private Set<Cartoon> likes;

  @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
  @JoinTable(
      name = "bookmarks",
      joinColumns = {@JoinColumn(name = "user_id")},
      inverseJoinColumns = {@JoinColumn(name = "cartoon_id")}
  )
  @ToString.Exclude
  private Set<Cartoon> bookmarks;

  public boolean isAdmin() {
    return roles.contains(ManicUserRole.ADMIN);
  }

  public void addRoles(Set<ManicUserRole> newRoles) {
    if (isNull(roles)) {
      roles = new HashSet<>();
    }
    roles.addAll(newRoles);
  }

  public void removeRoles(Set<ManicUserRole> rolesToRemove) {
    if (nonNull(roles)) {
      roles.removeAll(rolesToRemove);
    }
    // All users always have the NORMAL role
    roles.add(ManicUserRole.NORMAL);
  }

  public void addLike(Cartoon cartoon) {
    likes.add(cartoon);
    cartoon.getLikedBy().add(this);
  }

  public void removeLike(Cartoon cartoon) {
    likes.remove(cartoon);
    cartoon.getLikedBy().remove(this);
  }

  public void addBookmark(Cartoon cartoon) {
    bookmarks.add(cartoon);
    cartoon.getBookmarkedBy().add(this);
  }

  public void removeBookmark(Cartoon cartoon) {
    bookmarks.remove(cartoon);
    cartoon.getBookmarkedBy().remove(this);
  }

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
