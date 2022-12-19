package com.cherrysoft.manics.web.v2.controller;

import com.cherrysoft.manics.web.v2.dto.pages.PageDTO;
import com.cherrysoft.manics.web.v2.dto.validation.OnCreate;
import com.cherrysoft.manics.mappers.v2.PageMapperV2;
import com.cherrysoft.manics.model.v2.PageV2;
import com.cherrysoft.manics.service.v2.PageServiceV2;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(PageController.BASE_URL)
public class PageController {
  public static final String BASE_URL = "/pages";
  private final PageServiceV2 pageService;
  private final PageMapperV2 mapper;

  @GetMapping("/{id}")
  public ResponseEntity<PageDTO> getPageById(@PathVariable Long id) {
    PageV2 result = pageService.getPageById(id);
    return ResponseEntity.ok(mapper.toDto(result));
  }

  @GetMapping
  public ResponseEntity<List<PageDTO>> getChapterPages(
      @RequestParam Long chapterId
  ) {
    List<PageV2> result = pageService.getChapterPages(chapterId);
    return ResponseEntity.ok(mapper.toDtoList(result));
  }

  @PostMapping
  @Validated(OnCreate.class)
  public ResponseEntity<PageDTO> createPage(
      @RequestParam Long chapterId,
      @RequestBody @Valid PageDTO pageDto
  ) {
    PageV2 newPage = mapper.toPage(pageDto);
    PageV2 result = pageService.createPage(chapterId, newPage);
    return ResponseEntity.ok(mapper.toDto(result));
  }

  @PatchMapping("/{id}")
  public ResponseEntity<PageDTO> updatePage(
      @PathVariable Long id,
      @RequestBody @Valid PageDTO pageDto
  ) {
    PageV2 updatedPage = mapper.toPage(pageDto);
    PageV2 result = pageService.updatePage(id, updatedPage);
    return ResponseEntity.ok(mapper.toDto(result));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<PageDTO> deletePage(
      @PathVariable Long id
  ) {
    PageV2 result = pageService.deletePage(id);
    return ResponseEntity.ok(mapper.toDto(result));
  }

}
