package com.manics.rest.rest;

import com.manics.rest.model.core.Story;
import com.manics.rest.service.search.SearchService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("search")
@AllArgsConstructor
public class SearchRest {
  private final SearchService searchService;

  @GetMapping()
  public ResponseEntity<List<Story>> searchStoriesByUrlImage(@RequestParam(name = "urlImage") String urlImage) {
    return ResponseEntity.ok().body(searchService.searchStoryByImage(urlImage));
  }

  @GetMapping("/stories")
  public ResponseEntity<List<Story>> searchStoriesByName(@RequestParam(name = "q") String name) {
    return ResponseEntity.ok().body(searchService.searchStoryByName(name));
  }

}
