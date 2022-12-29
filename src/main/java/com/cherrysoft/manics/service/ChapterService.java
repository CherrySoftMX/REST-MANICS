package com.cherrysoft.manics.service;

import com.cherrysoft.manics.exception.ChapterNotFoundException;
import com.cherrysoft.manics.model.Cartoon;
import com.cherrysoft.manics.model.Chapter;
import com.cherrysoft.manics.model.search.SearchChapterResult;
import com.cherrysoft.manics.repository.ChapterRepository;
import com.cherrysoft.manics.service.search.SearchingChapterService;
import com.cherrysoft.manics.util.BeanUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChapterService {
  private final CartoonService cartoonService;
  private final SearchingChapterService searchingChapterService;
  private final ChapterRepository chapterRepository;

  public List<Chapter> searchChapterByName(String name, Pageable pageable) {
    SearchChapterResult searchResult = searchingChapterService.searchByChapterName(name, pageable);
    return chapterRepository.findAllById(searchResult.getMatchingChapterIds());
  }

  public Chapter getChapterById(Long id) {
    return chapterRepository
        .findById(id)
        .orElseThrow(() -> new ChapterNotFoundException(id));
  }

  Chapter getChapterReferenceById(Long id) {
    return chapterRepository.getReferenceById(id);
  }

  public List<Chapter> getCartoonChapters(Long cartoonId, Pageable pageable) {
    return chapterRepository.findChaptersByCartoon_Id(cartoonId, pageable);
  }

  @Transactional
  public Chapter createChapter(Long cartoonId, Chapter newChapter) {
    Cartoon cartoon = cartoonService.getCartonReferenceById(cartoonId);
    newChapter.setCartoon(cartoon);
    Chapter result = chapterRepository.save(newChapter);
    searchingChapterService.indexChapterForSearching(result);
    return result;
  }

  @Transactional
  public Chapter updateChapter(Long id, Chapter updatedChapter) {
    Chapter chapter = getChapterById(id);
    updatedChapter.setPages(null);
    BeanUtils.copyProperties(updatedChapter, chapter);
    Chapter result = chapterRepository.save(chapter);
    searchingChapterService.updateIndexedChapter(result);
    return result;
  }

  @Transactional
  public Chapter deleteChapter(Long id) {
    Chapter chapter = getChapterById(id);
    searchingChapterService.deleteIndexedChapter(chapter);
    chapterRepository.deleteById(id);
    return chapter;
  }

}
