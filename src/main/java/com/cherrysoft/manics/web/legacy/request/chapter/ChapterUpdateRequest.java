package com.cherrysoft.manics.web.legacy.request.chapter;

import com.cherrysoft.manics.web.legacy.request.page.PageUpdateRequest;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

public class ChapterUpdateRequest {

  @NotNull
  private Integer chapterNumber;

  @NotEmpty
  @NotNull
  private String name;

  @NotNull
  private String publicationDate;

  @NotNull
  private Integer totalPages;

  private List<PageUpdateRequest> pages;

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

  public List<PageUpdateRequest> getPages() {
    return pages;
  }

  public void setPages(List<PageUpdateRequest> pages) {
    this.pages = pages;
  }

  @Override
  public String toString() {
    return "ChapterUpdateRequest{" + "chapterNumber=" + chapterNumber + ", name='" + name + '\''
        + ", publicationDate='" + publicationDate + '\'' + ", totalPages=" + totalPages;
  }
}
