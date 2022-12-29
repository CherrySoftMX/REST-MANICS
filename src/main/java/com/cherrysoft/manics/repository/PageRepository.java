package com.cherrysoft.manics.repository;

import com.cherrysoft.manics.model.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PageRepository extends JpaRepository<Page, Long> {

  List<Page> findPagesByChapter_Id(Long chapterId, Pageable pageable);

}
