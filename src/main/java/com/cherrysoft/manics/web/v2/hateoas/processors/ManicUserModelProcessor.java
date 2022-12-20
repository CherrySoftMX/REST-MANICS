package com.cherrysoft.manics.web.v2.hateoas.processors;

import com.cherrysoft.manics.service.v2.BookmarkService;
import com.cherrysoft.manics.service.v2.LikeService;
import com.cherrysoft.manics.web.v2.controller.BookmarkController;
import com.cherrysoft.manics.web.v2.controller.CommentController;
import com.cherrysoft.manics.web.v2.controller.LikeController;
import com.cherrysoft.manics.web.v2.controller.SuggestionController;
import com.cherrysoft.manics.web.v2.dto.users.ManicUserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelProcessor;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
@RequiredArgsConstructor
public class ManicUserModelProcessor implements RepresentationModelProcessor<ManicUserDTO> {
  private final LikeService likeService;
  private final BookmarkService bookmarkService;
  private ManicUserDTO userModel;

  @Override
  @NonNull
  public ManicUserDTO process(@NonNull ManicUserDTO userModel) {
    this.userModel = userModel;
    if (likeService.userHasLikedAnyCartoon(userModel.getId())) {
      userModel.add(selfLikesLink());
    }
    if (bookmarkService.userHasBookmarkedAnyCartoon(userModel.getId())) {
      userModel.add(selfBookmarksLink());
    }
    userModel.add(List.of(likeLink(), bookmarkLink(), commentLink(), suggestLink()));
    return userModel;
  }

  private Link selfLikesLink() {
    return WebMvcLinkBuilder.linkTo(methodOn(LikeController.class)
            .getLikes(userModel.getId()))
        .withRel("selfLikes");
  }

  private Link likeLink() {
    return linkTo(methodOn(LikeController.class)
        .like(null, null, userModel.getId())).withRel("like");
  }

  private Link selfBookmarksLink() {
    return WebMvcLinkBuilder.linkTo(methodOn(BookmarkController.class)
        .getBookmarks(userModel.getId())).withRel("selfBookmarks");
  }

  private Link bookmarkLink() {
    return linkTo(methodOn(BookmarkController.class)
        .bookmark(null, null, userModel.getId())).withRel("bookmark");
  }

  private Link commentLink() {
    return linkTo(methodOn(CommentController.class)
        .createComment(null, null, userModel.getId(), null)).withRel("comment");
  }

  private Link suggestLink() {
    return WebMvcLinkBuilder.linkTo(methodOn(SuggestionController.class)
        .createSuggestion(null, userModel.getId(), null)).withRel("suggest");
  }

}
