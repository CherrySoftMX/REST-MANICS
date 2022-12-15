package com.cherrysoft.manics.repository;

import com.cherrysoft.manics.model.core.Page;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PageRepository extends CrudRepository<Page, Integer> {

  List<Page> getPagesByChapter_ChapterId(Integer chapterId);

}
