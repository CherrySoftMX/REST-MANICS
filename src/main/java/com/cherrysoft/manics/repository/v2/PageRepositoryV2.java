package com.cherrysoft.manics.repository.v2;

import com.cherrysoft.manics.model.v2.PageV2;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PageRepositoryV2 extends JpaRepository<PageV2, Long> {

  List<PageV2> findPageV2sByChapter_Id(Long chapterId);

}
