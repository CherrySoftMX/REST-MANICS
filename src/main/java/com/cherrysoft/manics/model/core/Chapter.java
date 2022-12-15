package com.cherrysoft.manics.model.core;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

import static java.util.Objects.isNull;

@Entity
@Table(name = "chapters")
@Inheritance(strategy = InheritanceType.JOINED)
public class Chapter {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "chapter_id")
  private Integer chapterId;

  @ManyToOne
  @JoinColumn(name = "story_id")
  private Story story;

  @Column
  private Integer chapterNumber;

  @Column
  private String name;

  @Column
  private String publicationDate;

  @Column
  private Integer totalPages;

  @OneToMany(mappedBy = "chapter", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  @JsonIgnore
  private List<Page> pages;

  public Integer getChapterId() {
    return chapterId;
  }

  @JsonIgnore
  public Story getStory() {
    return story;
  }

  public void setStory(Story story) {
    this.story = story;
  }

  public Integer getChapterNumber() {
    return chapterNumber;
  }

  public void setChapterNumber(Integer chapterNumber) {
    this.chapterNumber = chapterNumber;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getPublicationDate() {
    return publicationDate;
  }

  public void setPublicationDate(String publicationDate) {
    this.publicationDate = publicationDate;
  }

  public Integer getTotalPages() {
    return totalPages;
  }

  public void setTotalPages(Integer totalPages) {
    this.totalPages = totalPages;
  }

  public List<Page> getPages() {
    return pages;
  }

  public void setPages(List<Page> pages) {
    this.pages = pages;
  }

  public void updateChapter(Chapter chapter) {
    setChapterNumber(chapter.getChapterNumber());
    setName(chapter.getName());
    setPublicationDate(chapter.getPublicationDate());
    setTotalPages(chapter.getTotalPages());
    if (!isNull(chapter.getPages())) {
      chapter.getPages().forEach((page) -> page.setChapter(this));
      setPages(chapter.getPages());
    }
  }

  @Override
  public String toString() {
    return "Chapter{" + "chapterId=" + chapterId + ", story=" + story + ", chapterNumber=" + chapterNumber
        + ", name='" + name + '\'' + ", publicationDate=" + publicationDate + ", totalPages=" + totalPages
        + ", pages=" + pages + '}';
  }

}
