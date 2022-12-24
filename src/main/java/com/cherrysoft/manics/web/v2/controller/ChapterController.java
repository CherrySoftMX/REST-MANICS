package com.cherrysoft.manics.web.v2.controller;

import com.cherrysoft.manics.model.v2.ChapterV2;
import com.cherrysoft.manics.service.v2.ChapterServiceV2;
import com.cherrysoft.manics.web.v2.dto.chapters.ChapterDTO;
import com.cherrysoft.manics.web.v2.dto.chapters.ChapterResponseDTO;
import com.cherrysoft.manics.web.v2.dto.validation.OnCreate;
import com.cherrysoft.manics.web.v2.mapper.v2.ChapterMapperV2;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(ChapterController.BASE_URL)
public class ChapterController {
  public static final String BASE_URL = "/chapters";
  private final ChapterServiceV2 chapterService;
  private final ChapterMapperV2 mapper;

  @GetMapping("/search")
  public ResponseEntity<List<ChapterResponseDTO>> searchChaptersByName(
      @RequestParam String name,
      @PageableDefault Pageable pageable
  ) {
    List<ChapterV2> searchResult = chapterService.searchChapterByName(name, pageable);
    return ResponseEntity.ok(mapper.toResponseListDto(searchResult));
  }

  @GetMapping("/{id}")
  public ResponseEntity<ChapterResponseDTO> getChapterById(@PathVariable Long id) {
    ChapterV2 result = chapterService.getChapterById(id);
    return ResponseEntity.ok(mapper.toResponseDto(result));
  }

  @GetMapping
  public ResponseEntity<List<ChapterResponseDTO>> getCartoonChapters(
      @RequestParam Long cartoonId,
      @PageableDefault @SortDefault(sort = "publicationDate") Pageable pageable
  ) {
    List<ChapterV2> result = chapterService.getCartoonChapters(cartoonId, pageable);
    return ResponseEntity.ok(mapper.toResponseListDto(result));
  }

  @PostMapping
  @Validated(OnCreate.class)
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  public ResponseEntity<ChapterResponseDTO> createChapter(
      @RequestParam Long cartoonId,
      @RequestBody @Valid ChapterDTO chapterDto
  ) {
    ChapterV2 newChapter = mapper.toChapter(chapterDto);
    ChapterV2 result = chapterService.createChapter(cartoonId, newChapter);
    return ResponseEntity.ok(mapper.toResponseDto(result));
  }

  @PatchMapping("/{id}")
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  public ResponseEntity<ChapterResponseDTO> updateChapter(
      @PathVariable Long id,
      @RequestBody @Valid ChapterDTO chapterDto
  ) {
    ChapterV2 updatedChapter = mapper.toChapter(chapterDto);
    ChapterV2 result = chapterService.updateChapter(id, updatedChapter);
    return ResponseEntity.ok(mapper.toResponseDto(result));
  }

  @DeleteMapping("/{id}")
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  public ResponseEntity<ChapterResponseDTO> deleteChapter(
      @PathVariable Long id
  ) {
    ChapterV2 result = chapterService.deleteChapter(id);
    return ResponseEntity.ok(mapper.toResponseDto(result));
  }

}
