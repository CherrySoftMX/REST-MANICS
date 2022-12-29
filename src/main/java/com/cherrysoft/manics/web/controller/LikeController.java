package com.cherrysoft.manics.web.controller;

import com.cherrysoft.manics.model.Cartoon;
import com.cherrysoft.manics.model.LikedResult;
import com.cherrysoft.manics.model.auth.ManicUser;
import com.cherrysoft.manics.model.specs.LikeSpec;
import com.cherrysoft.manics.security.SecurityManicUser;
import com.cherrysoft.manics.service.LikeService;
import com.cherrysoft.manics.web.dto.LikedResultDTO;
import com.cherrysoft.manics.web.dto.cartoons.CartoonResponseDTO;
import com.cherrysoft.manics.web.dto.users.ManicUserDTO;
import com.cherrysoft.manics.web.mapper.CartoonMapper;
import com.cherrysoft.manics.web.mapper.LikedResultMapper;
import com.cherrysoft.manics.web.mapper.ManicUserMapper;
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
@RequestMapping(LikeController.BASE_URL)
public class LikeController {
  public static final String BASE_URL = "/likes";
  private final LikeService likeService;
  private final LikedResultMapper likeMapper;
  private final CartoonMapper cartoonMapper;
  private final ManicUserMapper userMapper;

  @GetMapping("/cartoon")
  public ResponseEntity<List<ManicUserDTO>> getLikedBy(
      @RequestParam Long cartoonId,
      @PageableDefault Pageable pageable
  ) {
    List<ManicUser> result = likeService.getLikedBy(cartoonId, pageable);
    return ResponseEntity.ok(userMapper.toDtoList(result));
  }

  @GetMapping("/user")
  public ResponseEntity<List<CartoonResponseDTO>> getLikes(
      @RequestParam Long userId,
      @PageableDefault Pageable pageable
  ) {
    List<Cartoon> result = likeService.getLikes(userId, pageable);
    return ResponseEntity.ok(cartoonMapper.toReponseDtoList(result));
  }

  @PostMapping
  @PreAuthorize("#loggedUser.id == #userId")
  public ResponseEntity<LikedResultDTO> like(
      @AuthenticationPrincipal SecurityManicUser loggedUser,
      @RequestParam Long cartoonId,
      @RequestParam Long userId
  ) {
    var likeSpec = new LikeSpec(cartoonId, userId);
    LikedResult result = likeService.like(likeSpec);
    return ResponseEntity.ok(likeMapper.toDto(result));
  }

}
