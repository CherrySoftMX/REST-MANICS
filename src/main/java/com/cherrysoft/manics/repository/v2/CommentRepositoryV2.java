package com.cherrysoft.manics.repository.v2;

import com.cherrysoft.manics.model.v2.CommentV2;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepositoryV2 extends JpaRepository<CommentV2, Long> {

  List<CommentV2> findCommentV2sByCartoon_Id(Long cartoonId);

  List<CommentV2> findCommentV2sByUser_Id(Long userId);

}
