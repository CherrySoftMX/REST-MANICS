package com.cherrysoft.manics.web.v2.controller;

import com.cherrysoft.manics.model.v2.Cartoon;
import com.cherrysoft.manics.model.v2.LikedResult;
import com.cherrysoft.manics.model.v2.auth.ManicUser;
import com.cherrysoft.manics.model.v2.specs.LikeSpec;
import com.cherrysoft.manics.security.SecurityManicUser;
import com.cherrysoft.manics.service.v2.LikeService;
import com.cherrysoft.manics.web.v2.dto.LikedResultDTO;
import com.cherrysoft.manics.web.v2.dto.cartoons.CartoonResponseDTO;
import com.cherrysoft.manics.web.v2.dto.users.ManicUserDTO;
import com.cherrysoft.manics.web.v2.mapper.v2.CartoonMapper;
import com.cherrysoft.manics.web.v2.mapper.v2.LikedResultMapper;
import com.cherrysoft.manics.web.v2.mapper.v2.ManicUserMapper;
import lombok.RequiredArgsConstructor;
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
      @RequestParam Long cartoonId
  ) {
    List<ManicUser> result = likeService.getLikedBy(cartoonId);
    return ResponseEntity.ok(userMapper.toDtoList(result));
  }

  @GetMapping("/user")
  public ResponseEntity<List<CartoonResponseDTO>> getLikes(
      @RequestParam Long userId
  ) {
    List<Cartoon> result = likeService.getLikes(userId);
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
