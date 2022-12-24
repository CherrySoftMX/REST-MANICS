package com.cherrysoft.manics.web.v2.mapper.v2;

import com.cherrysoft.manics.model.v2.search.SearchByImageResult;
import com.cherrysoft.manics.web.v2.dto.search.SearchByImageResultDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {PageMapperV2.class})
public interface SearchByImageResultMapper {

  SearchByImageResultDTO toDto(SearchByImageResult result);

}
