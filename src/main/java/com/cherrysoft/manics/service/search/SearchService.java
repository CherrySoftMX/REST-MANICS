package com.cherrysoft.manics.service.search;

import com.cherrysoft.manics.model.legacy.core.Story;
import com.cherrysoft.manics.model.legacy.core.elasticsearch.StorySearch;
import com.cherrysoft.manics.service.elasticsearch.ImageAnalyzerService;
import com.cherrysoft.manics.service.stories.StoryService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class SearchService {
  private final ImageAnalyzerService imageAnalyzerService;
  private final StorySearchService storySearchService;
  private final StoryService storyService;

  public List<Story> searchStoryByImage(String urlImage) {
    Double[] vectorRepresentation;
    List<Story> stories = new ArrayList<>();
    try {
      vectorRepresentation = imageAnalyzerService.analyzeImageFormUrl(urlImage);
      List<StorySearch> storySearchs = storySearchService.scoreByDenseVector(vectorRepresentation);
      stories = storySearchs.stream().map((storySearch) -> storyService.getStoryById(storySearch.getStoryId()))
          .collect(Collectors.toList());

    } catch (IOException e) {

    }

    return stories;
  }

  public List<Story> searchStoryByName(String name) {
    List<StorySearch> searchs = storySearchService.findAllByName(name);
    List<Story> stories = searchs.stream().map((s) -> storyService.getStoryById(s.getStoryId()))
        .collect(Collectors.toList());
    return stories;
  }
}
