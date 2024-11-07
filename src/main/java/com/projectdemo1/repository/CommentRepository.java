package com.projectdemo1.repository;

import com.projectdemo1.domain.Board;
import com.projectdemo1.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByBoardAndParentIsNull(Board board);// 최상위 게시물 가져오기


}
