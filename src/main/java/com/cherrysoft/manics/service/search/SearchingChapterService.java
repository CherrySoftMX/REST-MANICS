package com.cherrysoft.manics.service.search;

import com.cherrysoft.manics.model.Chapter;
import com.cherrysoft.manics.model.Page;
import com.cherrysoft.manics.model.search.SearchChapterResult;
import com.cherrysoft.manics.model.search.SearchingChapter;
import com.cherrysoft.manics.repository.search.SearchingChapterRepository;
import com.cherrysoft.manics.service.search.mapper.SearchingChapterMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Slf4j
@Service
@RequiredArgsConstructor
public class SearchingChapterService {
  private final SearchingPageService searchingPageService;
  private final SearchingChapterMapper searchingChapterMapper;
  private final SearchingChapterRepository searchingChapterRepository;

  public SearchChapterResult searchByChapterName(String name, Pageable pageable) {
    List<SearchingChapter> matchingChapters = searchingChapterRepository.findByNameLike(name, pageable);
    return new SearchChapterResult(matchingChapters);
  }

  public void indexChapterForSearching(Chapter chapter) {
    SearchingChapter searchingChapter = searchingChapterMapper.toSearchingChapter(chapter);
    searchingPageService.indexPagesForSearching(chapter.getPages());
    searchingChapterRepository.save(searchingChapter);
    log.debug("Index chapter document: {}", searchingChapter);
  }

  public void indexChaptersForSearching(List<Chapter> chapters) {
    List<SearchingChapter> searchingChapters = searchingChapterMapper.toList(chapters);
    searchingPageService.indexPagesForSearching(extractAllPages(chapters));
    searchingChapterRepository.saveAll(searchingChapters);
    log.debug("Indexed chapter documents: {}", searchingChapters);
  }

  public void updateIndexedChapter(Chapter chapter) {
    SearchingChapter searchingChapter = searchingChapterMapper.toSearchingChapter(chapter);
    searchingChapterRepository.save(searchingChapter);
    log.debug("Updated chapter document: {}", searchingChapter);
  }

  public void deleteIndexedChapters(List<Chapter> chapters) {
    List<SearchingChapter> searchingChapters = searchingChapterMapper.toList(chapters);
    searchingPageService.deleteIndexedPages(extractAllPages(chapters));
    searchingChapterRepository.deleteAll(searchingChapters);
    log.debug("Deleted chapters documents: {}", searchingChapters);
  }

  public void deleteIndexedChapter(Chapter chapter) {
    SearchingChapter searchingChapter = searchingChapterMapper.toSearchingChapter(chapter);
    searchingPageService.deleteIndexedPages(chapter.getPages());
    searchingChapterRepository.delete(searchingChapter);
    log.debug("Deleted chapter document: {}", searchingChapter);
  }

  private List<Page> extractAllPages(List<Chapter> chapters) {
    return chapters.stream()
        .map(Chapter::getPages)
        .flatMap(Collection::stream)
        .collect(toList());
  }

}
