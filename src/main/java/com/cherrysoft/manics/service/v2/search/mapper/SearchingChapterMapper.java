package com.cherrysoft.manics.service.v2.search.mapper;

import com.cherrysoft.manics.model.v2.ChapterV2;
import com.cherrysoft.manics.model.v2.search.SearchingChapter;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SearchingChapterMapper {

  SearchingChapter toSearchingChapter(ChapterV2 chapter);

  List<SearchingChapter> toList(List<ChapterV2> chapters);

}
