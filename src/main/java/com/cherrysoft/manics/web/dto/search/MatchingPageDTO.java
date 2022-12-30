package com.cherrysoft.manics.web.dto.search;

import com.cherrysoft.manics.web.dto.pages.CartoonPageDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Data
@EqualsAndHashCode(callSuper = true)
@Relation(collectionRelation = "matchingPages", itemRelation = "matchingPage")
public class MatchingPageDTO extends RepresentationModel<MatchingPageDTO> {
  private CartoonPageDTO page;
  private double score;
}
