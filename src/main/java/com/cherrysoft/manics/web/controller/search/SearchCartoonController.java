package com.cherrysoft.manics.web.controller.search;

import com.cherrysoft.manics.model.Cartoon;
import com.cherrysoft.manics.model.search.SearchByImageResult;
import com.cherrysoft.manics.service.CartoonService;
import com.cherrysoft.manics.service.search.SearchByImageService;
import com.cherrysoft.manics.web.dto.cartoons.CartoonResponseDTO;
import com.cherrysoft.manics.web.dto.search.SearchByImageResultDTO;
import com.cherrysoft.manics.web.mapper.CartoonMapper;
import com.cherrysoft.manics.web.mapper.SearchByImageResultMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.cherrysoft.manics.util.ApiDocsConstants.BAD_REQUEST_RESPONSE_REF;
import static com.cherrysoft.manics.util.ApiDocsConstants.UNAUTHORIZED_RESPONSE_REF;

@RestController
@RequestMapping(SearchCartoonController.BASE_URL)
@RequiredArgsConstructor
@Tag(name = "Search", description = "Search cartoon by name or image")
@ApiResponses({
    @ApiResponse(ref = BAD_REQUEST_RESPONSE_REF, responseCode = "400"),
    @ApiResponse(ref = UNAUTHORIZED_RESPONSE_REF, responseCode = "401"),
    @ApiResponse(description = "Internal server error", responseCode = "500", content = @Content)
})
public class SearchCartoonController {
  public static final String BASE_URL = "/cartoons/search";
  private final CartoonService cartoonService;
  private final SearchByImageService searchByImageService;
  private final CartoonMapper cartoonMapper;
  private final SearchByImageResultMapper searchResultMapper;

  @Operation(summary = "Returns the cartoons in which its name matches with the given name")
  @ApiResponse(responseCode = "200", description = "OK", content = {
      @Content(array = @ArraySchema(schema = @Schema(implementation = CartoonResponseDTO.class)))
  })
  @GetMapping
  public ResponseEntity<List<CartoonResponseDTO>> searchCartoons(
      @RequestParam String name,
      @PageableDefault @ParameterObject Pageable pageable
  ) {
    List<Cartoon> result = cartoonService.searchCartoonByName(name, pageable);
    return ResponseEntity.ok(cartoonMapper.toReponseDtoList(result));
  }

  @Operation(summary = "Returns the cartoons that have similar pages to the provided image")
  @ApiResponse(responseCode = "200", description = "OK", content = {
      @Content(schema = @Schema(implementation = SearchByImageResultDTO.class))
  })
  @GetMapping("/img")
  public ResponseEntity<SearchByImageResultDTO> searchCartoonByImage(
      @RequestParam String imgUrl,
      @PageableDefault @ParameterObject Pageable pageable
  ) {
    SearchByImageResult result = searchByImageService.searchPagesByImage(imgUrl, pageable);
    return ResponseEntity.ok(searchResultMapper.toDto(result));
  }

}
