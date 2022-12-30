package com.cherrysoft.manics.service.search;

import com.cherrysoft.manics.model.CartoonPage;
import com.cherrysoft.manics.model.search.SearchingPage;
import com.cherrysoft.manics.repository.search.SearchingPageRepository;
import com.cherrysoft.manics.service.search.mapper.SearchingPageMapper;
import com.cherrysoft.manics.service.search.utils.ImageAnalysisUtils;
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

  public void indexPagesForSearching(List<CartoonPage> pages) {
    pages.forEach(this::indexPageForSearching);
  }

  public void indexPageForSearching(CartoonPage page) {
    SearchingPage searchingPage = searchingPageMapper.toSearchingPage(page);
    try {
      tryIndexPageForSearching(page, searchingPage);
    } catch (IOException e) {
      log.error("Failed to index page: {} | Error message: {}", searchingPage, e.getMessage());
    }
  }

  public void updateIndexedPage(CartoonPage page) {
    SearchingPage searchingPage = searchingPageMapper.toSearchingPage(page);
    try {
      tryIndexPageForSearching(page, searchingPage);
    } catch (IOException e) {
      log.error("Failed to update indexed page: {} | Error message: {}", searchingPage, e.getMessage());
    }
  }

  private void tryIndexPageForSearching(CartoonPage page, SearchingPage searchingPage) throws IOException {
    searchingPage.setImgVector(ImageAnalysisUtils.convertImgUrlToVector(page.getImageUrl()));
    searchingPageRepository.save(searchingPage);
  }

  public void deleteIndexedPages(List<CartoonPage> pages) {
    List<SearchingPage> searchingPages = searchingPageMapper.toList(pages);
    searchingPageRepository.deleteAll(searchingPages);
  }

  public void deleteIndexedPage(CartoonPage page) {
    SearchingPage searchingPage = searchingPageMapper.toSearchingPage(page);
    searchingPageRepository.delete(searchingPage);
  }

}
