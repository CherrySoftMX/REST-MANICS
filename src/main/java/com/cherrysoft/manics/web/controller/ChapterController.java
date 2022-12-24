package com.cherrysoft.manics.web.controller;

import com.cherrysoft.manics.model.Chapter;
import com.cherrysoft.manics.service.ChapterService;
import com.cherrysoft.manics.web.dto.chapters.ChapterDTO;
import com.cherrysoft.manics.web.dto.chapters.ChapterResponseDTO;
import com.cherrysoft.manics.web.dto.validation.OnCreate;
import com.cherrysoft.manics.web.mapper.ChapterMapper;
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
  private final ChapterService chapterService;
  private final ChapterMapper mapper;

  @GetMapping("/search")
  public ResponseEntity<List<ChapterResponseDTO>> searchChaptersByName(
      @RequestParam String name,
      @PageableDefault Pageable pageable
  ) {
    List<Chapter> searchResult = chapterService.searchChapterByName(name, pageable);
    return ResponseEntity.ok(mapper.toResponseListDto(searchResult));
  }

  @GetMapping("/{id}")
  public ResponseEntity<ChapterResponseDTO> getChapterById(@PathVariable Long id) {
    Chapter result = chapterService.getChapterById(id);
    return ResponseEntity.ok(mapper.toResponseDto(result));
  }

  @GetMapping
  public ResponseEntity<List<ChapterResponseDTO>> getCartoonChapters(
      @RequestParam Long cartoonId,
      @PageableDefault @SortDefault(sort = "publicationDate") Pageable pageable
  ) {
    List<Chapter> result = chapterService.getCartoonChapters(cartoonId, pageable);
    return ResponseEntity.ok(mapper.toResponseListDto(result));
  }

  @PostMapping
  @Validated(OnCreate.class)
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  public ResponseEntity<ChapterResponseDTO> createChapter(
      @RequestParam Long cartoonId,
      @RequestBody @Valid ChapterDTO chapterDto
  ) {
    Chapter newChapter = mapper.toChapter(chapterDto);
    Chapter result = chapterService.createChapter(cartoonId, newChapter);
    return ResponseEntity.ok(mapper.toResponseDto(result));
  }

  @PatchMapping("/{id}")
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  public ResponseEntity<ChapterResponseDTO> updateChapter(
      @PathVariable Long id,
      @RequestBody @Valid ChapterDTO chapterDto
  ) {
    Chapter updatedChapter = mapper.toChapter(chapterDto);
    Chapter result = chapterService.updateChapter(id, updatedChapter);
    return ResponseEntity.ok(mapper.toResponseDto(result));
  }

  @DeleteMapping("/{id}")
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  public ResponseEntity<ChapterResponseDTO> deleteChapter(
      @PathVariable Long id
  ) {
    Chapter result = chapterService.deleteChapter(id);
    return ResponseEntity.ok(mapper.toResponseDto(result));
  }

}
