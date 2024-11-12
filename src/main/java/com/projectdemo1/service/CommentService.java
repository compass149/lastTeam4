package com.projectdemo1.service;

import com.projectdemo1.domain.Comment;
import com.projectdemo1.dto.CommentDTO;
import com.projectdemo1.dto.PageRequestDTO;
import com.projectdemo1.dto.PageResponseDTO;

import java.util.List;

public interface CommentService {
//    public Comment createComment(Long boardId, CommentDTO commentDTO);
//
//    public List<Comment> getComments(Long boardId);
//    public void deleteComment(Long id);
 Comment createComment(Long boardId, CommentDTO commentDTO);
    void register(Comment comment); // 댓글 작성
    List<Comment> listByBoard(Long bno); // 특정 게시글의 댓글 목록 조회
    void modify(Comment comment); // 댓글 수정
    Comment findById(Long cno); // 댓글 조회
     List<Comment> getComments(Long boardId);
     void deleteComment(Long id);
    Long register(CommentDTO commentDTO);
    CommentDTO read (Long rno);
    void modify(CommentDTO commentDTO);
    void remove(Long rno);

    PageResponseDTO<CommentDTO> getListOfBoard (Long bno, PageRequestDTO pageRequestDTO);
}
