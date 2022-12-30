package com.cherrysoft.manics.web.hateoas.processors;

import com.cherrysoft.manics.web.controller.BookmarkController;
import com.cherrysoft.manics.web.controller.CommentController;
import com.cherrysoft.manics.web.controller.LikeController;
import com.cherrysoft.manics.web.controller.SuggestionController;
import com.cherrysoft.manics.web.dto.users.ManicUserDTO;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelProcessor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class ManicUserModelProcessor implements RepresentationModelProcessor<ManicUserDTO> {
  private ManicUserDTO userModel;

  @Override
  @NonNull
  public ManicUserDTO process(@NonNull ManicUserDTO userModel) {
    this.userModel = userModel;
    userModel.add(List.of(commentsLink(), suggestionsLink(), likesLink(), bookmarksLink()));
    return userModel;
  }

  private Link commentsLink() {
    Map<String, String> params = Map.of("userId", userModel.getId().toString());
    return linkTo(methodOn(CommentController.class)
        .getComments(params, null))
        .withRel("comments");
  }

  private Link suggestionsLink() {
    return linkTo(methodOn(SuggestionController.class)
        .getSuggestionsOfUser(null, userModel.getId(), null))
        .withRel("suggestions");
  }

  private Link likesLink() {
    return linkTo(methodOn(LikeController.class)
        .getLikes(userModel.getId(), null))
        .withRel("likes");
  }

  private Link bookmarksLink() {
    return linkTo(methodOn(BookmarkController.class)
        .getBookmarks(userModel.getId(), null))
        .withRel("bookmarks");
  }

}
