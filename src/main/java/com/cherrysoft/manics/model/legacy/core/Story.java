package com.cherrysoft.manics.model.legacy.core;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.cherrysoft.manics.model.legacy.auth.User;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.util.Objects.isNull;

@Entity
@Table(name = "stories")
@Inheritance(strategy = InheritanceType.JOINED)
public class Story {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "story_id")
  private Integer id;

  @Column
  private String name;

  @Column
  private String author;

  @Column
  private Integer publicationYear;

  @Column
  private Integer availableChapters;

  @OneToOne
  @JoinColumn(name = "category_id")
  private Category category;

  @OneToMany(mappedBy = "story", cascade = CascadeType.ALL)
  @JsonIgnore
  private List<Chapter> chapters;

  @OneToMany(mappedBy = "story", cascade = CascadeType.ALL)
  @JsonIgnore
  private List<Comment> comments;

  @ManyToMany(mappedBy = "likes")
  @JsonIgnore
  private Set<User> likedBy = new HashSet<>();

  public Story() {

  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getAuthor() {
    return author;
  }

  public void setAuthor(String author) {
    this.author = author;
  }

  public Integer getPublicationYear() {
    return publicationYear;
  }

  public void setPublicationYear(Integer publicationYear) {
    this.publicationYear = publicationYear;
  }

  public Integer getAvailableChapters() {
    return availableChapters;
  }

  public void setAvailableChapters(Integer availableChapters) {
    this.availableChapters = availableChapters;
  }

  public Category getCategory() {
    return category;
  }

  public void setCategory(Category category) {
    this.category = category;
  }

  public List<Chapter> getChapters() {
    return chapters;
  }

  public void setChapters(List<Chapter> chapters) {
    this.chapters = chapters;
  }

  public List<Comment> getComments() {
    return comments;
  }

  public Set<User> getLikedBy() {
    return likedBy;
  }

  public Integer getLikesCount() {
    return likedBy.size();
  }

  public void updateStory(Category category, Story story) {
    setCategory(category);
    setName(story.getName());
    setAuthor(story.getAuthor());
    setAvailableChapters(story.getAvailableChapters());
    setPublicationYear(story.getPublicationYear());
    if (!isNull(story.chapters)) {
      story.getChapters().forEach((chapter) -> chapter.setStory(this));
      setChapters(story.getChapters());
    }
  }

  public void addLikedBy(User user) {
    this.likedBy.add(user);
  }

  public void removeLikedBy(Integer userId) {
    this.likedBy.removeIf(user -> user.getUserId().equals(userId));
  }

  public boolean isLikedBy(Integer userId) {
    return getLikedBy().stream().anyMatch(user -> user.getUserId().equals(userId));
  }

  @Override
  public String toString() {
    return "Story{" + "storyId=" + id + ", name='" + name + '\'' + ", author='" + author + '\''
        + ", publicationYear=" + publicationYear + ", availableChapters=" + availableChapters + ", category="
        + category + '}';
  }

}
