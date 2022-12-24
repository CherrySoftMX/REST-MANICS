package com.cherrysoft.manics.service.v2.search;

import com.cherrysoft.manics.model.v2.PageV2;
import com.cherrysoft.manics.model.v2.search.SearchingPage;
import com.cherrysoft.manics.repository.v2.search.SearchingPageRepository;
import com.cherrysoft.manics.service.v2.search.mapper.SearchingPageMapper;
import com.cherrysoft.manics.service.v2.search.utils.ImageAnalysisUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class SearchingPageService {
  private final SearchingPageMapper searchingPageMapper;
  private final SearchingPageRepository searchingPageRepository;

  public void indexPagesForSearching(List<PageV2> pages) {
    pages.forEach(this::indexPageForSearching);
  }

  public void indexPageForSearching(PageV2 page) {
    SearchingPage searchingPage = searchingPageMapper.toSearchingPage(page);
    try {
      tryIndexPageForSearching(page, searchingPage);
    } catch (IOException e) {
      log.error("Failed to index page: {} | Error message: {}", searchingPage, e.getMessage());
    }
  }

  public void updateIndexedPage(PageV2 page) {
    SearchingPage searchingPage = searchingPageMapper.toSearchingPage(page);
    try {
      tryIndexPageForSearching(page, searchingPage);
    } catch (IOException e) {
      log.error("Failed to update indexed page: {} | Error message: {}", searchingPage, e.getMessage());
    }
  }

  private void tryIndexPageForSearching(PageV2 page, SearchingPage searchingPage) throws IOException {
    searchingPage.setImgVector(ImageAnalysisUtils.convertImgUrlToVector(page.getImageUrl()));
    searchingPageRepository.save(searchingPage);
  }

  public void deleteIndexedPages(List<PageV2> pages) {
    List<SearchingPage> searchingPages = searchingPageMapper.toList(pages);
    searchingPageRepository.deleteAll(searchingPages);
  }

  public void deleteIndexedPage(PageV2 page) {
    SearchingPage searchingPage = searchingPageMapper.toSearchingPage(page);
    searchingPageRepository.delete(searchingPage);
  }

}
