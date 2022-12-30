package com.cherrysoft.manics.web.hateoas.utils;

import com.cherrysoft.manics.model.Cartoon;
import com.cherrysoft.manics.model.CartoonType;
import com.cherrysoft.manics.web.controller.cartoons.ComicController;
import com.cherrysoft.manics.web.controller.cartoons.MangaController;
import org.springframework.hateoas.Link;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

public class CartoonLinkUtils {

  public static Link getCartoonLink(Cartoon cartoon) {
    String rel = "cartoon";
    if (cartoon.getType() == CartoonType.COMIC) {
      return linkTo(methodOn(ComicController.class)
          .getComicById(cartoon.getId()))
          .withRel(rel);
    }
    return linkTo(methodOn(MangaController.class)
        .getMangaById(cartoon.getId()))
        .withRel(rel);
  }

}
