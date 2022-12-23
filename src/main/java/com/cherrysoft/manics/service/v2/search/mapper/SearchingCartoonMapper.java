package com.cherrysoft.manics.service.v2.search.mapper;

import com.cherrysoft.manics.model.v2.Cartoon;
import com.cherrysoft.manics.model.v2.search.SearchingCartoon;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SearchingCartoonMapper {

  SearchingCartoon toSearchingCartoon(Cartoon cartoon);

}
