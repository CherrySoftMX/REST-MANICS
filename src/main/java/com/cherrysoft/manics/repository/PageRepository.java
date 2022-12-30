package com.cherrysoft.manics.repository;

import com.cherrysoft.manics.model.CartoonPage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PageRepository extends JpaRepository<CartoonPage, Long> {

  Page<CartoonPage> findPagesByChapter_Id(Long chapterId, Pageable pageable);

}
