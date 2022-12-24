package com.cherrysoft.manics.web.mapper;

import com.cherrysoft.manics.model.Chapter;
import com.cherrysoft.manics.web.dto.chapters.ChapterDTO;
import com.cherrysoft.manics.web.dto.chapters.ChapterResponseDTO;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ChapterMapper {

  @Mappings({
      @Mapping(target = "baseChapterFields.id", source = "id"),
      @Mapping(target = "baseChapterFields.name", source = "name"),
      @Mapping(target = "baseChapterFields.chapterNumber", source = "chapterNumber"),
      @Mapping(target = "baseChapterFields.totalPages", source = "totalPages"),
      @Mapping(target = "baseChapterFields.publicationDate", source = "publicationDate"),
  })
  ChapterDTO toDto(Chapter chapter);

  @Mappings({
      @Mapping(target = "baseChapterFields.id", source = "id"),
      @Mapping(target = "baseChapterFields.name", source = "name"),
      @Mapping(target = "baseChapterFields.chapterNumber", source = "chapterNumber"),
      @Mapping(target = "baseChapterFields.totalPages", source = "totalPages"),
      @Mapping(target = "baseChapterFields.publicationDate", source = "publicationDate"),
  })
  ChapterResponseDTO toResponseDto(Chapter chapter);

  List<ChapterResponseDTO> toResponseListDto(List<Chapter> chapter);

  @InheritInverseConfiguration
  Chapter toChapter(ChapterDTO chapterDto);

}
