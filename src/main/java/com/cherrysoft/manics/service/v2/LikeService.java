package com.cherrysoft.manics.service.v2;

import com.cherrysoft.manics.model.v2.Cartoon;
import com.cherrysoft.manics.model.v2.LikedResult;
import com.cherrysoft.manics.model.v2.auth.ManicUser;
import com.cherrysoft.manics.model.v2.specs.LikeSpec;
import com.cherrysoft.manics.repository.v2.CartoonRepository;
import com.cherrysoft.manics.repository.v2.users.ManicUserRepository;
import com.cherrysoft.manics.service.v2.users.ManicUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LikeService {
  private final ManicUserService userService;
  private final ManicUserRepository userRepository;
  private final CartoonService cartoonService;
  private final CartoonRepository cartoonRepository;

  public List<ManicUser> getLikedBy(Long cartoonId) {
    return userRepository.getLikedBy(cartoonId);
  }

  public List<Cartoon> getLikes(Long userId) {
    return cartoonRepository.getLikes(userId);
  }

  @Transactional
  public LikedResult like(LikeSpec spec) {
    boolean wasLiked = isCartoonLiked(spec);
    Cartoon cartoon = getCartoon(spec.getCartoonId());
    ManicUser user = getUser(spec.getUserId());
    if (wasLiked) {
      user.removeLike(cartoon);
    } else {
      user.addLike(cartoon);
    }
    return new LikedResult(wasLiked, !wasLiked);
  }

  private boolean isCartoonLiked(LikeSpec spec) {
    return cartoonRepository.isCartoonLikedBy(spec.getCartoonId(), spec.getUserId());
  }

  private Cartoon getCartoon(Long cartoonId) {
    return cartoonService.getCartoonById(cartoonId);
  }

  private ManicUser getUser(Long userId) {
    return userService.getUserById(userId);
  }

}
