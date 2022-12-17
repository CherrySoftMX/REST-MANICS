package com.cherrysoft.manics.mappers.v2;

import com.cherrysoft.manics.controller.v2.dto.chapters.ChapterDTO;
import com.cherrysoft.manics.controller.v2.dto.chapters.ChapterResponseDTO;
import com.cherrysoft.manics.model.v2.ChapterV2;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ChapterMapperV2 {

  @Mappings({
      @Mapping(target = "baseChapterFields.name", source = "name"),
      @Mapping(target = "baseChapterFields.chapterNumber", source = "chapterNumber"),
      @Mapping(target = "baseChapterFields.totalPages", source = "totalPages"),
      @Mapping(target = "baseChapterFields.publicationDate", source = "publicationDate"),
  })
  ChapterDTO toDto(ChapterV2 chapter);

  @Mappings({
      @Mapping(target = "baseChapterFields.name", source = "name"),
      @Mapping(target = "baseChapterFields.chapterNumber", source = "chapterNumber"),
      @Mapping(target = "baseChapterFields.totalPages", source = "totalPages"),
      @Mapping(target = "baseChapterFields.publicationDate", source = "publicationDate"),
  })
  ChapterResponseDTO toResponseDto(ChapterV2 chapter);

  List<ChapterResponseDTO> toResponseListDto(List<ChapterV2> chapter);

  @InheritInverseConfiguration
  ChapterV2 toChapter(ChapterDTO chapterDto);

}
