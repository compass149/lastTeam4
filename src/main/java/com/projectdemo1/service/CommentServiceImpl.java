package com.projectdemo1.service;

import com.projectdemo1.domain.Board;
import com.projectdemo1.domain.Comment;
import com.projectdemo1.dto.CommentDTO;
import com.projectdemo1.repository.BoardRepository;
import com.projectdemo1.repository.CommentRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Log4j2
@RequiredArgsConstructor
@Transactional
public class CommentServiceImpl implements CommentService{
    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;

    @Override
    public Comment createComment(Long boardId, CommentDTO commentDTO) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new IllegalArgumentException("Post not found"));

        Comment comment = new Comment();
        comment.setContent(commentDTO.getContent());
        comment.setWriter(commentDTO.getWriter());
        comment.setBoard(board);

        if (commentDTO.getParentId()!= null) {
            Comment parentComment = commentRepository.findById(commentDTO.getParentId())
                    .orElseThrow(() -> new IllegalArgumentException("Parent comment not found"));
            comment.setParent(parentComment);
        }

        return commentRepository.save(comment);
    }

    @Override
    public List<Comment> getComments(Long boardId) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new IllegalArgumentException("Post not found"));
        //log.info("aaaaaaaaaaaaaaaaa"+commentRepository.findByBoardAndParentIsNull());

        return commentRepository.findByBoardAndParentIsNull(board);
    }

    @Override
    public void deleteComment(Long id) {
        commentRepository.deleteById(id);
    }
}
