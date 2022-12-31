package com.cherrysoft.manics.web.mapper;

import com.cherrysoft.manics.model.Suggestion;
import com.cherrysoft.manics.web.dto.SuggestionDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SuggestionMapper {

  SuggestionDTO toDto(Suggestion suggestion);

  Suggestion toSuggestion(SuggestionDTO suggestionDto);

}
