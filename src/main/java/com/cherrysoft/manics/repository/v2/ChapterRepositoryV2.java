package com.cherrysoft.manics.repository.v2;

import com.cherrysoft.manics.model.v2.ChapterV2;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChapterRepositoryV2 extends JpaRepository<ChapterV2, Long> {

  List<ChapterV2> findChapterV2sByCartoon_Id(Long cartoonId);

}
