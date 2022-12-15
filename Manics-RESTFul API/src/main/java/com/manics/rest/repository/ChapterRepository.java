package com.manics.rest.repository;

import com.manics.rest.model.core.Chapter;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface ChapterRepository extends CrudRepository<Chapter, Integer> {

  List<Chapter> getChaptersByStory_Id(Integer storyId);

}
