package com.cherrysoft.manics.service.v2.search.mapper;

import com.cherrysoft.manics.model.v2.PageV2;
import com.cherrysoft.manics.model.v2.search.SearchingPage;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SearchingPageMapper {

  SearchingPage toSearchingPage(PageV2 page);

  List<SearchingPage> toList(List<PageV2> pages);

}
