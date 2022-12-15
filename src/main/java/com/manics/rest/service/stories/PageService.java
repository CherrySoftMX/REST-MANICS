package com.manics.rest.service.stories;

import com.manics.rest.exception.BadRequestException;
import com.manics.rest.exception.NotFoundException;
import com.manics.rest.model.core.Chapter;
import com.manics.rest.model.core.Page;
import com.manics.rest.model.core.Story;
import com.manics.rest.repository.PageRepository;
import com.manics.rest.service.search.StorySearchService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class PageService<T extends Story> {
  private final StorySearchService searchService;
  private final ChapterService<T> chapterService;
  private final PageRepository pageRepository;

  public List<Page> getPages(Integer storyId, Integer chapterId, Class<T> clazz) {
    chapterService.checkIfChapterExists(storyId, chapterId, clazz);
    return pageRepository.getPagesByChapter_ChapterId(chapterId);
  }

  public Page getPageById(Integer pageId) {
    return pageRepository.findById(pageId)
        .orElseThrow(
            () -> new NotFoundException(String.format("La página con el id: %d no existe", pageId))
        );
  }

  public Page getPage(Integer storyId, Integer chapterId, Integer pageId, Class<T> clazz) {
    getPages(storyId, chapterId, clazz)
        .stream()
        .filter(page -> page.getPageId().equals(pageId))
        .findAny()
        .orElseThrow(
            () -> new BadRequestException(String.format("El capítulo con el id: %s no tiene ninguna página con el id: %s", chapterId, pageId))
        );

    return getPageById(pageId);
  }


  public Page createPage(Integer storyId, Integer chapterId, Page page, Class<T> clazz) {
    Chapter chapter = chapterService.getChapter(storyId, chapterId, clazz);
    page.setChapter(chapter);
    Page newPage = pageRepository.save(page);
    searchService.indexPage(storyId, newPage);
    return newPage;
  }

  public Page updatePage(Integer storyId, Integer chapterId, Integer pageId, Page newPage, Class<T> clazz) {
    Page page = getPage(storyId, chapterId, pageId, clazz);
    searchService.updateIndexedPage(storyId, page);
    page.updatePage(newPage);
    return pageRepository.save(page);
  }

  public Page deletePage(Integer storyId, Integer chapterId, Integer pageId, Class<T> clazz) {
    Page page = getPage(storyId, chapterId, pageId, clazz);
    searchService.deleteIndexedPage(storyId, page);
    pageRepository.deleteById(pageId);
    return page;
  }

}
