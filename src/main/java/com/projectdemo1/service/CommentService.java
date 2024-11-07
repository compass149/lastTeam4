package com.projectdemo1.service;

import com.projectdemo1.domain.Comment;
import com.projectdemo1.dto.CommentDTO;

import java.util.List;

public interface CommentService {
    public Comment createComment(Long boardId, CommentDTO commentDTO);

    public List<Comment> getComments(Long boardId);
    public void deleteComment(Long id);
}
