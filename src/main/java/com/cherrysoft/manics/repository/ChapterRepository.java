package com.cherrysoft.manics.repository;

import com.cherrysoft.manics.model.Chapter;
import com.cherrysoft.manics.repository.search.SearchingChapterRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChapterRepository extends JpaRepository<Chapter, Long>, SearchingChapterRepository {

  Page<Chapter> findChaptersByCartoon_Id(Long cartoonId, Pageable pageable);

}
