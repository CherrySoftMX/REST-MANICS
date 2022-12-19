package com.cherrysoft.manics.web.v2.controller;

import com.cherrysoft.manics.web.v2.dto.BookmarkedResultDTO;
import com.cherrysoft.manics.web.v2.dto.cartoons.CartoonResponseDTO;
import com.cherrysoft.manics.mappers.v2.BookmarkedResultMapper;
import com.cherrysoft.manics.mappers.v2.CartoonMapper;
import com.cherrysoft.manics.model.v2.BookmarkedResult;
import com.cherrysoft.manics.model.v2.Cartoon;
import com.cherrysoft.manics.model.v2.specs.BookmarkSpec;
import com.cherrysoft.manics.service.v2.BookmarkService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(BookmarkController.BASE_URL)
public class BookmarkController {
  public static final String BASE_URL = "/bookmarks";
  private final BookmarkService bookmarkService;
  private final BookmarkedResultMapper bookmarkMapper;
  private final CartoonMapper cartoonMapper;

  @GetMapping("/user")
  public ResponseEntity<List<CartoonResponseDTO>> getBookmarks(
      @RequestParam Long userId
  ) {
    List<Cartoon> result = bookmarkService.getBookmarks(userId);
    return ResponseEntity.ok(cartoonMapper.toReponseDtoList(result));
  }

  @PostMapping
  public ResponseEntity<BookmarkedResultDTO> bookmark(
      @RequestParam Long cartoonId,
      @RequestParam Long userId
  ) {
    var bookmarkSpec = new BookmarkSpec(cartoonId, userId);
    BookmarkedResult result = bookmarkService.bookmark(bookmarkSpec);
    return ResponseEntity.ok(bookmarkMapper.toDto(result));
  }

}
