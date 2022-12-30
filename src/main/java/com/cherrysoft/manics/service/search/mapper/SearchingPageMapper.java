package com.cherrysoft.manics.service.search.mapper;

import com.cherrysoft.manics.model.CartoonPage;
import com.cherrysoft.manics.model.search.SearchingPage;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SearchingPageMapper {

  SearchingPage toSearchingPage(CartoonPage page);

  List<SearchingPage> toList(List<CartoonPage> pages);

}
