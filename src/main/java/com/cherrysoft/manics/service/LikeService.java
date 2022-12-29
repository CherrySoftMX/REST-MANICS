package com.cherrysoft.manics.service;

import com.cherrysoft.manics.model.Cartoon;
import com.cherrysoft.manics.model.LikedResult;
import com.cherrysoft.manics.model.auth.ManicUser;
import com.cherrysoft.manics.model.specs.LikeSpec;
import com.cherrysoft.manics.repository.CartoonRepository;
import com.cherrysoft.manics.repository.users.ManicUserRepository;
import com.cherrysoft.manics.service.users.ManicUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LikeService {
  private final ManicUserService userService;
  private final ManicUserRepository userRepository;
  private final CartoonService cartoonService;
  private final CartoonRepository cartoonRepository;

  public List<ManicUser> getLikedBy(Long cartoonId, Pageable pageable) {
    return userRepository.getLikedBy(cartoonId, pageable);
  }

  public List<Cartoon> getLikes(Long userId, Pageable pageable) {
    return cartoonRepository.getLikes(userId, pageable);
  }

  public boolean userHasLikedAnyCartoon(Long userId) {
    return cartoonRepository.hasUserLikedAnyCartoon(userId);
  }

  public LikedResult like(LikeSpec spec) {
    boolean wasLiked = isCartoonLiked(spec);
    Cartoon cartoon = getCartoon(spec.getCartoonId());
    ManicUser user = getUser(spec.getUserId());
    if (wasLiked) {
      user.removeLike(cartoon);
    } else {
      user.addLike(cartoon);
    }
    userRepository.saveAndFlush(user);
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
