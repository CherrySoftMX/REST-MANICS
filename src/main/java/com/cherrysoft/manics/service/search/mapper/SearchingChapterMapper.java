package com.cherrysoft.manics.service.search.mapper;

import com.cherrysoft.manics.model.Chapter;
import com.cherrysoft.manics.model.search.SearchingChapter;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SearchingChapterMapper {

  SearchingChapter toSearchingChapter(Chapter chapter);

  List<SearchingChapter> toList(List<Chapter> chapters);

}
