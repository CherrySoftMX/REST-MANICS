package com.cherrysoft.manics.service.v2;

import com.cherrysoft.manics.exception.v2.PageNotFoundException;
import com.cherrysoft.manics.model.v2.ChapterV2;
import com.cherrysoft.manics.model.v2.PageV2;
import com.cherrysoft.manics.repository.v2.PageRepositoryV2;
import com.cherrysoft.manics.util.BeanUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PageServiceV2 {
  private final ChapterServiceV2 chapterService;
  private final PageRepositoryV2 pageRepository;

  public PageV2 getPageById(Long id) {
    return pageRepository
        .findById(id)
        .orElseThrow(() -> new PageNotFoundException(id));
  }

  public List<PageV2> getChapterPages(Long chapterId, Pageable pageable) {
    return pageRepository.findPageV2sByChapter_Id(chapterId, pageable);
  }

  public PageV2 createPage(Long chapterId, PageV2 newPage) {
    ChapterV2 chapter = chapterService.getChapterReferenceById(chapterId);
    newPage.setChapter(chapter);
    return pageRepository.save(newPage);
  }

  public PageV2 updatePage(Long id, PageV2 updatedPage) {
    PageV2 page = getPageById(id);
    BeanUtils.copyProperties(updatedPage, page);
    return pageRepository.save(page);
  }

  public PageV2 deletePage(Long id) {
    PageV2 page = getPageById(id);
    pageRepository.deleteById(id);
    return page;
  }

}
