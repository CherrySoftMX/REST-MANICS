package com.cherrysoft.manics.web.mapper;

import com.cherrysoft.manics.model.Suggestion;
import com.cherrysoft.manics.web.dto.SuggestionDTO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SuggestionMapper {

  SuggestionDTO toDto(Suggestion suggestion);

  List<SuggestionDTO> toDtoList(List<Suggestion> suggestions);

  Suggestion toSuggestion(SuggestionDTO suggestionDto);

}
