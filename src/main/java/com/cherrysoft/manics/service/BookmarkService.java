package com.cherrysoft.manics.service;

import com.cherrysoft.manics.model.BookmarkedResult;
import com.cherrysoft.manics.model.Cartoon;
import com.cherrysoft.manics.model.auth.ManicUser;
import com.cherrysoft.manics.model.specs.BookmarkSpec;
import com.cherrysoft.manics.repository.CartoonRepository;
import com.cherrysoft.manics.repository.users.ManicUserRepository;
import com.cherrysoft.manics.service.users.ManicUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookmarkService {
  private final ManicUserService userService;
  private final ManicUserRepository userRepository;
  private final CartoonService cartoonService;
  private final CartoonRepository cartoonRepository;

  public List<Cartoon> getBookmarks(Long userId, Pageable pageable) {
    return cartoonRepository.getBookmarks(userId, pageable);
  }

  public boolean userHasBookmarkedAnyCartoon(Long userId) {
    return cartoonRepository.hasUserBookmarkedAnyCartoon(userId);
  }

  public BookmarkedResult bookmark(BookmarkSpec spec) {
    boolean wasBookmarked = isCartoonBookmarked(spec);
    Cartoon cartoon = getCartoon(spec.getCartoonId());
    ManicUser user = getUser(spec.getUserId());
    if (wasBookmarked) {
      user.removeBookmark(cartoon);
    } else {
      user.addBookmark(cartoon);
    }
    userRepository.saveAndFlush(user);
    return new BookmarkedResult(wasBookmarked, !wasBookmarked);
  }

  private boolean isCartoonBookmarked(BookmarkSpec spec) {
    return cartoonRepository.isCartoonBookmarkedBy(spec.getCartoonId(), spec.getUserId());
  }

  private Cartoon getCartoon(Long cartoonId) {
    return cartoonService.getCartoonById(cartoonId);
  }

  private ManicUser getUser(Long userId) {
    return userService.getUserById(userId);
  }

}
