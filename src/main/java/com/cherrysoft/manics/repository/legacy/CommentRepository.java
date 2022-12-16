package com.cherrysoft.manics.repository.legacy;

import com.cherrysoft.manics.model.legacy.core.Comment;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends CrudRepository<Comment, Integer> {

  List<Comment> findByStory_Id(Integer storyId);

  List<Comment> findByUserId(Integer userId);

}
