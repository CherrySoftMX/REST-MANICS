package com.cherrysoft.manics.service.search.mapper;

import com.cherrysoft.manics.model.Cartoon;
import com.cherrysoft.manics.model.search.SearchingCartoon;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SearchingCartoonMapper {

  SearchingCartoon toSearchingCartoon(Cartoon cartoon);

}
