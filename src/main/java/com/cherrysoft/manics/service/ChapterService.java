package com.cherrysoft.manics.service;

import com.cherrysoft.manics.exception.ChapterNotFoundException;
import com.cherrysoft.manics.model.Cartoon;
import com.cherrysoft.manics.model.Chapter;
import com.cherrysoft.manics.repository.ChapterRepository;
import com.cherrysoft.manics.service.search.SearchingPageService;
import com.cherrysoft.manics.util.BeanUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ChapterService {
  private final CartoonService cartoonService;
  private final ChapterRepository chapterRepository;
  private final SearchingPageService searchingPageService;

  public Page<Chapter> searchChapterByName(String name, Pageable pageable) {
    return chapterRepository.searchChapterByName(name, pageable);
  }

  public Chapter getChapterById(Long id) {
    return chapterRepository
        .findById(id)
        .orElseThrow(() -> new ChapterNotFoundException(id));
  }

  Chapter getChapterReferenceById(Long id) {
    return chapterRepository.getReferenceById(id);
  }

  public Page<Chapter> getCartoonChapters(Long cartoonId, Pageable pageable) {
    return chapterRepository.findChaptersByCartoon_Id(cartoonId, pageable);
  }

  @Transactional
  public Chapter createChapter(Long cartoonId, Chapter newChapter) {
    Cartoon cartoon = cartoonService.getCartonReferenceById(cartoonId);
    newChapter.setCartoon(cartoon);
    Chapter result = chapterRepository.save(newChapter);
    searchingPageService.indexPagesForSearching(result.getPages());
    return result;
  }

  public Chapter updateChapter(Long id, Chapter updatedChapter) {
    Chapter chapter = getChapterById(id);
    updatedChapter.setPages(null);
    BeanUtils.copyProperties(updatedChapter, chapter);
    return chapterRepository.save(chapter);
  }

  @Transactional
  public Chapter deleteChapter(Long id) {
    Chapter chapter = getChapterById(id);
    chapterRepository.deleteById(id);
    searchingPageService.deleteIndexedPages(chapter.getPages());
    return chapter;
  }

}
