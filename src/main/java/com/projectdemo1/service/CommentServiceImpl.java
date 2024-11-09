package com.projectdemo1.service;

import com.projectdemo1.domain.Board;
import com.projectdemo1.domain.Comment;
import com.projectdemo1.dto.CommentDTO;
import com.projectdemo1.dto.PageRequestDTO;
import com.projectdemo1.dto.PageResponseDTO;
import com.projectdemo1.repository.BoardRepository;
import com.projectdemo1.repository.CommentRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
@Transactional
public class CommentServiceImpl implements CommentService{
    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;
    private final ModelMapper modelMapper;


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

    @Override
    public Long register(CommentDTO commentDTO) {
        return 0L;
    }

    @Override
    public CommentDTO read(Long rno) {
        return null;
    }

    @Override
    public void modify(CommentDTO commentDTO) {

    }

    @Override
    public void remove(Long rno) {

    }

    @Override
    public PageResponseDTO<CommentDTO> getListOfBoard(Long bno, PageRequestDTO pageRequestDTO) {
        Pageable pageable = PageRequest.of(pageRequestDTO.getPage() <= 0 ? 0 :
                        pageRequestDTO.getPage() - 1,
                pageRequestDTO.getSize(),
                Sort.by("rno").ascending());

        Page<Comment> result = commentRepository.listOfBoard(bno, pageable);

        List<CommentDTO> dtoList = result.getContent().stream().map(comment -> modelMapper.map(comment, CommentDTO.class))
                .collect(Collectors.toList());

        return PageResponseDTO.<CommentDTO>withAll()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(dtoList)
                .total((int) result.getTotalElements())
                .build();
    }
}
