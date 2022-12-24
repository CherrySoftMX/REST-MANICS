package com.cherrysoft.manics.service.search.mapper;

import com.cherrysoft.manics.model.Page;
import com.cherrysoft.manics.model.search.SearchingPage;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SearchingPageMapper {

  SearchingPage toSearchingPage(Page page);

  List<SearchingPage> toList(List<Page> pages);

}
