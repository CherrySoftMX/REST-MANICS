package com.manics.rest.model.core;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name = "pages")
@Inheritance(strategy = InheritanceType.JOINED)
public class Page {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column
  private Integer pageId;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "chapter_id")
  private Chapter chapter;

  @Column
  private Integer pageNumber;

  @Column
  private String imageUrl;

  public Integer getPageId() {
    return pageId;
  }

  @JsonIgnore
  public Chapter getChapter() {
    return chapter;
  }

  public void setChapter(Chapter chapter) {
    this.chapter = chapter;
  }

  public Integer getPageNumber() {
    return pageNumber;
  }

  public void setPageNumber(Integer pageNumber) {
    this.pageNumber = pageNumber;
  }

  public String getImageUrl() {
    return imageUrl;
  }

  public void setImageUrl(String imageUrl) {
    this.imageUrl = imageUrl;
  }

  public void updatePage(Page page) {
    setImageUrl(page.getImageUrl());
    setPageNumber(page.getPageNumber());
  }

  @Override
  public String toString() {
    return "Page{" + "pageId=" + pageId + ", pageNumber=" + pageNumber + ", imageUrl='" + imageUrl + '\'' + '}';
  }

}
