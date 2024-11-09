package com.projectdemo1.repository;

import com.projectdemo1.domain.Board;
import com.projectdemo1.domain.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByBoardAndParentIsNull(Board board);// 최상위 게시물 가져오기

    @Query("select r from Comment r where r.board.bno = :bno")
    Page<Comment> listOfBoard(Long bno, Pageable pageable);
}
