package com.cherrysoft.manics.service.v2;

import com.cherrysoft.manics.exception.v2.ChapterNotFoundException;
import com.cherrysoft.manics.model.v2.Cartoon;
import com.cherrysoft.manics.model.v2.ChapterV2;
import com.cherrysoft.manics.repository.v2.ChapterRepositoryV2;
import com.cherrysoft.manics.util.BeanUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChapterServiceV2 {
  private final CartoonService cartoonService;
  private final ChapterRepositoryV2 chapterRepository;

  public ChapterV2 getChapterById(Long id) {
    return chapterRepository
        .findById(id)
        .orElseThrow(() -> new ChapterNotFoundException(id));
  }

  public List<ChapterV2> getChaptersByCartoonId(Long cartoonId) {
    return chapterRepository.findChapterV2sByCartoon_Id(cartoonId);
  }

  public ChapterV2 createChapter(Long cartoonId, ChapterV2 newChapter) {
    Cartoon cartoon = cartoonService.getCartonReferenceById(cartoonId);
    newChapter.setCartoon(cartoon);
    return chapterRepository.save(newChapter);
  }

  public ChapterV2 updateChapter(Long id, ChapterV2 updatedChapter) {
    ChapterV2 chapter = getChapterById(id);
    updatedChapter.setPages(null);
    BeanUtils.copyProperties(updatedChapter, chapter);
    return chapterRepository.save(chapter);
  }

  public ChapterV2 deleteChapter(Long id) {
    ChapterV2 chapter = getChapterById(id);
    chapterRepository.deleteById(id);
    return chapter;
  }

}
