package com.cherrysoft.manics.service;

import com.cherrysoft.manics.exception.PageNotFoundException;
import com.cherrysoft.manics.model.Chapter;
import com.cherrysoft.manics.model.Page;
import com.cherrysoft.manics.repository.PageRepository;
import com.cherrysoft.manics.service.search.SearchingPageService;
import com.cherrysoft.manics.util.BeanUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.Objects.nonNull;

@Service
@RequiredArgsConstructor
public class PageService {
  private final ChapterService chapterService;
  private final SearchingPageService searchingPageService;
  private final PageRepository pageRepository;

  public Page getPageById(Long id) {
    return pageRepository
        .findById(id)
        .orElseThrow(() -> new PageNotFoundException(id));
  }

  public List<Page> getChapterPages(Long chapterId, Pageable pageable) {
    return pageRepository.findPagesByChapter_Id(chapterId, pageable);
  }

  @Transactional
  public Page createPage(Long chapterId, Page newPage) {
    Chapter chapter = chapterService.getChapterReferenceById(chapterId);
    newPage.setChapter(chapter);
    Page result = pageRepository.save(newPage);
    searchingPageService.indexPageForSearching(result);
    return result;
  }

  @Transactional
  public Page updatePage(Long id, Page updatedPage) {
    Page page = getPageById(id);
    BeanUtils.copyProperties(updatedPage, page);
    Page result = pageRepository.save(page);
    if (nonNull(updatedPage.getImageUrl())) {
      searchingPageService.updateIndexedPage(result);
    }
    return result;
  }

  @Transactional
  public Page deletePage(Long id) {
    Page page = getPageById(id);
    searchingPageService.deleteIndexedPage(page);
    pageRepository.deleteById(id);
    return page;
  }

}
