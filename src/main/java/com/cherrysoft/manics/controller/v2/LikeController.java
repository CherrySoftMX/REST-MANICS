package com.cherrysoft.manics.controller.v2;

import com.cherrysoft.manics.controller.v2.dto.LikedResultDTO;
import com.cherrysoft.manics.controller.v2.dto.cartoons.CartoonResponseDTO;
import com.cherrysoft.manics.controller.v2.dto.users.ManicUserDTO;
import com.cherrysoft.manics.mappers.v2.CartoonMapper;
import com.cherrysoft.manics.mappers.v2.LikedResultMapper;
import com.cherrysoft.manics.mappers.v2.ManicUserMapper;
import com.cherrysoft.manics.model.v2.Cartoon;
import com.cherrysoft.manics.model.v2.LikedResult;
import com.cherrysoft.manics.model.v2.auth.ManicUser;
import com.cherrysoft.manics.model.v2.specs.LikeSpec;
import com.cherrysoft.manics.service.v2.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
  public ResponseEntity<LikedResultDTO> like(
      @RequestParam Long cartoonId,
      @RequestParam Long userId
  ) {
    var likeSpec = new LikeSpec(cartoonId, userId);
    LikedResult result = likeService.like(likeSpec);
    return ResponseEntity.ok(likeMapper.toDto(result));
  }

}
