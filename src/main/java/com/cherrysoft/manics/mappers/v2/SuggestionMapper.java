package com.cherrysoft.manics.mappers.v2;

import com.cherrysoft.manics.controller.v2.dto.SuggestionDTO;
import com.cherrysoft.manics.model.v2.SuggestionV2;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SuggestionMapper {

  SuggestionDTO toDto(SuggestionV2 suggestion);

  List<SuggestionDTO> toDtoList(List<SuggestionV2> suggestions);

  SuggestionV2 toSuggestion(SuggestionDTO suggestionDto);

}
