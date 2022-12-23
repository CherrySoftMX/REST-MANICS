package com.cherrysoft.manics.service.v2;

import com.cherrysoft.manics.exception.v2.ChapterNotFoundException;
import com.cherrysoft.manics.model.v2.Cartoon;
import com.cherrysoft.manics.model.v2.ChapterV2;
import com.cherrysoft.manics.model.v2.search.SearchChapterResult;
import com.cherrysoft.manics.repository.v2.ChapterRepositoryV2;
import com.cherrysoft.manics.service.v2.search.SearchingChapterService;
import com.cherrysoft.manics.util.BeanUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChapterServiceV2 {
  private final CartoonService cartoonService;
  private final SearchingChapterService searchingChapterService;
  private final ChapterRepositoryV2 chapterRepository;

  public List<ChapterV2> searchChapterByName(String name, Pageable pageable) {
    SearchChapterResult searchResult = searchingChapterService.searchByChapterName(name, pageable);
    return chapterRepository.findAllById(searchResult.getMatchingChapterIds());
  }

  public ChapterV2 getChapterById(Long id) {
    return chapterRepository
        .findById(id)
        .orElseThrow(() -> new ChapterNotFoundException(id));
  }

  ChapterV2 getChapterReferenceById(Long id) {
    return chapterRepository.getReferenceById(id);
  }

  public List<ChapterV2> getCartoonChapters(Long cartoonId, Pageable pageable) {
    return chapterRepository.findChapterV2sByCartoon_Id(cartoonId, pageable);
  }

  @Transactional
  public ChapterV2 createChapter(Long cartoonId, ChapterV2 newChapter) {
    Cartoon cartoon = cartoonService.getCartonReferenceById(cartoonId);
    newChapter.setCartoon(cartoon);
    ChapterV2 result = chapterRepository.save(newChapter);
    searchingChapterService.indexChapterForSearching(result);
    return result;
  }

  @Transactional
  public ChapterV2 updateChapter(Long id, ChapterV2 updatedChapter) {
    ChapterV2 chapter = getChapterById(id);
    updatedChapter.setPages(null);
    BeanUtils.copyProperties(updatedChapter, chapter);
    ChapterV2 result = chapterRepository.save(chapter);
    searchingChapterService.updateIndexedChapter(result);
    return result;
  }

  @Transactional
  public ChapterV2 deleteChapter(Long id) {
    ChapterV2 chapter = getChapterById(id);
    searchingChapterService.deleteIndexedChapter(chapter);
    chapterRepository.deleteById(id);
    return chapter;
  }

}
