package com.cherrysoft.manics.service.v2;

import com.cherrysoft.manics.model.v2.BookmarkedResult;
import com.cherrysoft.manics.model.v2.Cartoon;
import com.cherrysoft.manics.model.v2.auth.ManicUser;
import com.cherrysoft.manics.model.v2.specs.BookmarkSpec;
import com.cherrysoft.manics.repository.v2.CartoonRepository;
import com.cherrysoft.manics.service.v2.users.ManicUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookmarkService {
  private final ManicUserService userService;
  private final CartoonService cartoonService;
  private final CartoonRepository cartoonRepository;

  public List<Cartoon> getBookmarks(Long userId) {
    return cartoonRepository.getBookmarks(userId);
  }

  @Transactional
  public BookmarkedResult bookmark(BookmarkSpec spec) {
    boolean wasBookmarked = isCartoonBookmarked(spec);
    Cartoon cartoon = getCartoon(spec.getCartoonId());
    ManicUser user = getUser(spec.getUserId());
    if (wasBookmarked) {
      user.removeBookmark(cartoon);
    } else {
      user.addBookmark(cartoon);
    }
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
