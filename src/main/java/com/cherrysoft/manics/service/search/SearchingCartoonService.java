package com.cherrysoft.manics.service.search;

import com.cherrysoft.manics.exception.CartoonNotFoundException;
import com.cherrysoft.manics.model.Cartoon;
import com.cherrysoft.manics.model.search.SearchCartoonResult;
import com.cherrysoft.manics.model.search.SearchingCartoon;
import com.cherrysoft.manics.repository.search.SearchingCartoonRepository;
import com.cherrysoft.manics.service.search.mapper.SearchingCartoonMapper;
import com.cherrysoft.manics.util.BeanUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class SearchingCartoonService {
  private final SearchingChapterService searchingChapterService;
  private final SearchingCartoonMapper searchingCartoonMapper;
  private final SearchingCartoonRepository searchingCartoonRepository;

  public SearchCartoonResult searchByCartoonName(String name, Pageable pageable) {
    List<SearchingCartoon> matchingCartoons = searchingCartoonRepository.findByNameLike(name, pageable);
    return new SearchCartoonResult(matchingCartoons);
  }

  public SearchingCartoon getSearchingCartoon(Long id) {
    return searchingCartoonRepository
        .findById(id)
        .orElseThrow(() -> new CartoonNotFoundException(id));
  }

  public void indexCartoonForSearching(Cartoon cartoon) {
    SearchingCartoon searchingCartoon = searchingCartoonMapper.toSearchingCartoon(cartoon);
    searchingCartoonRepository.save(searchingCartoon);
    searchingChapterService.indexChaptersForSearching(cartoon.getChapters());
    log.debug("Indexed cartoon document: {}", searchingCartoon);
  }

  public void updateIndexedCartoon(Long id, Cartoon cartoon) {
    SearchingCartoon searchingCartoon = getSearchingCartoon(id);
    BeanUtils.copyProperties(cartoon, searchingCartoon);
    searchingCartoonRepository.save(searchingCartoon);
    log.debug("Updated cartoon document: {}", searchingCartoon);
  }

  public void deleteIndexedCartoon(Cartoon cartoon) {
    SearchingCartoon searchingCartoon = searchingCartoonMapper.toSearchingCartoon(cartoon);
    searchingChapterService.deleteIndexedChapters(cartoon.getChapters());
    searchingCartoonRepository.delete(searchingCartoon);
    log.debug("Deleted cartoon document: {}", searchingCartoon);
  }

}
