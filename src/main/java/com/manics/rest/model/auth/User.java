package com.manics.rest.model.auth;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.Sets;
import com.manics.rest.model.Suggestion;
import com.manics.rest.model.core.Story;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "users", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"username", "email"})
})
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer userId;

  @Column
  private String username;

  @Column
  private String email;

  @Column
  @JsonIgnore
  private String password;

  @ElementCollection(fetch = FetchType.EAGER)
  @JsonIgnore
  private Set<UserRole> roles = Sets.newHashSet(UserRole.NORMAL);

  @OneToMany(mappedBy = "user", orphanRemoval = true)
  @JsonIgnore
  private List<Suggestion> suggestions;

  @ManyToMany(cascade = CascadeType.ALL)
  @JoinTable(
      name = "likes",
      joinColumns = {@JoinColumn(name = "user_id")},
      inverseJoinColumns = {@JoinColumn(name = "story_id")}
  )
  @JsonIgnore
  private Set<Story> likes = new HashSet<>();

  @ManyToMany(cascade = CascadeType.ALL)
  @JoinTable(
      name = "read_later",
      joinColumns = {@JoinColumn(name = "user_id")},
      inverseJoinColumns = {@JoinColumn(name = "story_id")}
  )
  @JsonIgnore
  private Set<Story> readLater = new HashSet<>();

  public User() {

  }

  public User(String username, String email, String password) {
    this.username = username;
    this.email = email;
    this.password = password;
  }

  public User(String username, String email, String password, Set<UserRole> roles) {
    this(username, email, password);
    this.roles = roles;
  }

  public Integer getUserId() {
    return userId;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public List<Suggestion> getSuggestions() {
    return suggestions;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public Set<SimpleGrantedAuthority> getRoles() {
    return roles.stream()
        .flatMap(role -> role.getGrantedAuthorities().stream())
        .collect(Collectors.toSet());
  }

  public void setRoles(Set<UserRole> roles) {
    this.roles = roles;
  }

  public void setLikes(Set<Story> likes) {
    this.likes = likes;
  }

  public void addLike(Story story) {
    this.likes.add(story);
  }

  public void removeLike(Integer storyId) {
    this.likes.removeIf(story -> story.getId().equals(storyId));
  }

  public Set<Story> getReadLater() {
    return readLater;
  }

  public void setReadLater(Set<Story> readLater) {
    this.readLater = readLater;
  }

  public void addToReadLater(Story story) {
    readLater.add(story);
  }

  public void removeFromReadLater(Integer storyId) {
    readLater.removeIf(story -> story.getId().equals(storyId));
  }

  public boolean isSavedInReadLater(Integer storyId) {
    return readLater.stream().anyMatch(story -> story.getId().equals(storyId));
  }

  @Override
  public String toString() {
    return "User{" +
        "userId=" + userId +
        ", username='" + username + '\'' +
        ", password='" + password + '\'' +
        ", email='" + email + '\'' +
        '}';
  }

}