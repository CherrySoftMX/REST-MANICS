package com.cherrysoft.manics.web.mapper;

import com.cherrysoft.manics.model.search.SearchByImageResult;
import com.cherrysoft.manics.web.dto.search.SearchByImageResultDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {PageMapper.class})
public interface SearchByImageResultMapper {

  SearchByImageResultDTO toDto(SearchByImageResult result);

}
