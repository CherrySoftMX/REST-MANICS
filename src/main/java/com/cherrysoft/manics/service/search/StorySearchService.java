package com.cherrysoft.manics.service.search;

import com.cherrysoft.manics.model.legacy.core.elasticsearch.PageSearch;
import com.cherrysoft.manics.model.legacy.core.elasticsearch.StorySearch;
import com.cherrysoft.manics.repository.elasticsearch.StorySearchRepository;
import com.cherrysoft.manics.exception.NotFoundException;
import com.cherrysoft.manics.model.legacy.core.Chapter;
import com.cherrysoft.manics.model.legacy.core.Page;
import com.cherrysoft.manics.model.legacy.core.Story;
import com.cherrysoft.manics.service.elasticsearch.ImageAnalyzerService;
import lombok.AllArgsConstructor;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.StringQuery;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class StorySearchService {
  public static final String INDEX_PAGE_SEARCH = "stories";
  private final StorySearchRepository storySearchRepository;
  private final ImageAnalyzerService imageService;
  private final ElasticsearchOperations elasticsearchOperations;

  public List<StorySearch> getStories() {
    List<StorySearch> stories = new ArrayList<>();
    storySearchRepository.findAll().iterator().forEachRemaining(stories::add);
    return stories;
  }

  public StorySearch getStorySearch(Integer id) {
    return storySearchRepository.findById(id).orElseThrow(() -> new NotFoundException("F"));
  }

  public StorySearch saveStorySearch(Story story) {
    StorySearch storySearch = new StorySearch();
    storySearch.setStoryId(story.getId());
    storySearch.setName(story.getName());
    return storySearchRepository.save(storySearch);
  }

  public StorySearch deleteStorySearch(Integer id) {
    StorySearch storySearch = getStorySearch(id);
    storySearchRepository.delete(storySearch);
    return storySearch;
  }

  public StorySearch updateStorySearch(Integer id, Story story) {
    StorySearch storySearch = getStorySearch(id);
    storySearch.setStoryId(story.getId());
    storySearch.setName(story.getName());
    storySearchRepository.save(storySearch);
    return storySearch;
  }

  @Async
  public void indexPage(Integer storyId, Page page) {
    StorySearch storySearch = getStorySearch(storyId);
    PageSearch pageSearch = new PageSearch();
    pageSearch.setPageId(page.getPageId());
    try {
      pageSearch.setVector(imageService.analyzeImageFormUrl(page.getImageUrl()));
      List<PageSearch> pageSearchs = getPageSearchs(storySearch);
      pageSearchs.add(pageSearch);
      storySearch.setPages(pageSearchs);
      storySearchRepository.save(storySearch);
    } catch (IOException e) {
      e.printStackTrace();
    }

  }

  @Async
  public void updateIndexedPage(Integer storyId, Page page) {
    StorySearch storySearch = getStorySearch(storyId);
    List<PageSearch> pageSearchs = getPageSearchs(storySearch);
    pageSearchs.forEach((p) -> {
      if (p.getPageId().equals(page.getPageId())) {
        p.setPageId(page.getPageId());
        try {
          p.setVector(imageService.analyzeImageFormUrl(page.getImageUrl()));
        } catch (IOException e) {

        }
      }
    });
    storySearch.setPages(pageSearchs);
    storySearchRepository.save(storySearch);
  }

  public void deleteIndexedChapter(Integer storyId, Chapter chapter) {
    StorySearch storySearch = getStorySearch(storyId);
    List<PageSearch> pageSearchs = getPageSearchs(storySearch);
    List<Page> pages = chapter.getPages();
    pages.forEach((p) -> {
      pageSearchs.forEach((ps) -> {
        if (ps.getPageId().equals(p.getPageId())) {
          ps.setPageId(-1);
        }
      });
    });
    pageSearchs.removeIf((pageSearch) -> pageSearch.getPageId().equals(-1));
    storySearch.setPages(pageSearchs);
    storySearchRepository.save(storySearch);
  }

  public void deleteIndexedPage(Integer storyId, Page page) {
    StorySearch storySearch = getStorySearch(storyId);
    List<PageSearch> pageSearchs = getPageSearchs(storySearch);
    pageSearchs.removeIf((pageSearch) -> pageSearch.getPageId().equals(page.getPageId()));
    storySearch.setPages(pageSearchs);
    storySearchRepository.save(storySearch);
  }

  private List<PageSearch> getPageSearchs(StorySearch storySearch) {
    return Objects.isNull(storySearch.getPages()) ? new ArrayList<>() : storySearch.getPages();
  }

  public List<StorySearch> findAllByName(String name) {
    return storySearchRepository.findByNameLike(name);
  }

  public List<StorySearch> scoreByDenseVector(Double[] vector) {
    StringQuery query = new StringQuery(
        "{\"nested\":{\"path\":\"pages\",\"query\":{\"function_score\":{\"query\":{\"match_all\":{}},\"script_score\":{\"script\":{\"source\":\"1 / (1 + l2norm(params.query_vector, 'pages.vector_values'))\",\"params\":{\"query_vector\":"
            + Arrays.toString(vector) + "}}},\"score_mode\":\"max\"}}}}");

    SearchHits<StorySearch> stories = elasticsearchOperations.search(query, StorySearch.class,
        IndexCoordinates.of(INDEX_PAGE_SEARCH));

    return stories.get().map(SearchHit::getContent).collect(Collectors.toList());
  }

}
