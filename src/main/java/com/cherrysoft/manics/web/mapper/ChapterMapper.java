package com.cherrysoft.manics.web.mapper;

import com.cherrysoft.manics.model.Chapter;
import com.cherrysoft.manics.web.dto.chapters.ChapterDTO;
import com.cherrysoft.manics.web.dto.chapters.ChapterResponseDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ChapterMapper {

  ChapterDTO toDto(Chapter chapter);

  ChapterResponseDTO toResponseDto(Chapter chapter);

  Chapter toChapter(ChapterDTO chapterDto);

}
