package com.cherrysoft.manics.repository;

import com.cherrysoft.manics.model.Chapter;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChapterRepository extends JpaRepository<Chapter, Long> {

  List<Chapter> findChaptersByCartoon_Id(Long cartoonId, Pageable pageable);

}
