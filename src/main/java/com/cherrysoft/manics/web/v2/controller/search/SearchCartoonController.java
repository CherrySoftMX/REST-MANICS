package com.cherrysoft.manics.web.v2.controller.search;

import com.cherrysoft.manics.model.v2.Cartoon;
import com.cherrysoft.manics.model.v2.search.SearchByImageResult;
import com.cherrysoft.manics.service.v2.CartoonService;
import com.cherrysoft.manics.service.v2.search.SearchByImageService;
import com.cherrysoft.manics.web.v2.dto.cartoons.CartoonResponseDTO;
import com.cherrysoft.manics.web.v2.dto.search.SearchByImageResultDTO;
import com.cherrysoft.manics.web.v2.mapper.v2.CartoonMapper;
import com.cherrysoft.manics.web.v2.mapper.v2.SearchByImageResultMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(SearchCartoonController.BASE_URL)
@RequiredArgsConstructor
public class SearchCartoonController {
  public static final String BASE_URL = "/cartoons/search";
  private final CartoonService cartoonService;
  private final SearchByImageService searchByImageService;
  private final CartoonMapper cartoonMapper;
  private final SearchByImageResultMapper searchResultMapper;

  @GetMapping
  public ResponseEntity<List<CartoonResponseDTO>> searchCartoons(
      @RequestParam String name,
      @PageableDefault Pageable pageable
  ) {
    List<Cartoon> result = cartoonService.searchCartoonByName(name, pageable);
    return ResponseEntity.ok(cartoonMapper.toReponseDtoList(result));
  }

  @GetMapping("/img")
  public ResponseEntity<SearchByImageResultDTO> searchCartoonByImage(
      @RequestParam String imgUrl,
      @PageableDefault Pageable pageable
  ) {
    SearchByImageResult result = searchByImageService.searchPagesByImage(imgUrl, pageable);
    return ResponseEntity.ok(searchResultMapper.toDto(result));
  }

}
