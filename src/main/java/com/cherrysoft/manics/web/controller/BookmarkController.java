package com.cherrysoft.manics.web.controller;

import com.cherrysoft.manics.model.BookmarkedResult;
import com.cherrysoft.manics.model.Cartoon;
import com.cherrysoft.manics.model.specs.BookmarkSpec;
import com.cherrysoft.manics.security.SecurityManicUser;
import com.cherrysoft.manics.service.BookmarkService;
import com.cherrysoft.manics.web.dto.BookmarkedResultDTO;
import com.cherrysoft.manics.web.dto.cartoons.CartoonResponseDTO;
import com.cherrysoft.manics.web.mapper.BookmarkedResultMapper;
import com.cherrysoft.manics.web.mapper.CartoonMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
      @RequestParam Long userId,
      @PageableDefault Pageable pageable
  ) {
    List<Cartoon> result = bookmarkService.getBookmarks(userId, pageable);
    return ResponseEntity.ok(cartoonMapper.toReponseDtoList(result));
  }

  @PostMapping
  @PreAuthorize("#loggedUser.id == #userId")
  public ResponseEntity<BookmarkedResultDTO> bookmark(
      @AuthenticationPrincipal SecurityManicUser loggedUser,
      @RequestParam Long cartoonId,
      @RequestParam Long userId
  ) {
    var bookmarkSpec = new BookmarkSpec(cartoonId, userId);
    BookmarkedResult result = bookmarkService.bookmark(bookmarkSpec);
    return ResponseEntity.ok(bookmarkMapper.toDto(result));
  }

}
