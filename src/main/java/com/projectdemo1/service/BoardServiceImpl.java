package com.projectdemo1.service;

import com.projectdemo1.board4.domain.Cboard;
import com.projectdemo1.domain.Board;
import com.projectdemo1.domain.User;
import com.projectdemo1.dto.BoardDTO;
import com.projectdemo1.dto.BoardListReplyCountDTO;
import com.projectdemo1.dto.PageRequestDTO;
import com.projectdemo1.dto.PageResponseDTO;
import com.projectdemo1.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;

    @Override
    public void register(Board board, User user) {
        board.setUser(user);
        boardRepository.save(board);
    }

    @Override
    public List<Board> list() {
       // return List.of();
        return boardRepository.findAll();
    }

    @Override
    public Board findById(Long bno) {
        Board board = boardRepository.findById(bno).get();
        board.setHitCount(board.getHitCount() + 1);
        boardRepository.save(board);
        return board;
    }

    @Override
    public void modify(Board board) {
        Board b = boardRepository.findById(board.getBno()).get();
        b.setContent(board.getContent());
        b.setTitle(board.getTitle());
    }

    @Override
    public void remove(Long bno) {
        boardRepository.deleteById(bno);
    }

  /* 박경미 쌤 참조
  @Override
    public void insert(Board board, User user) {
        board.setUser(user);
        boardRepository.save(board);

    }

    @Override
    public List<Board> list() {
        return boardRepository.findAll();
    }

    @Override
    public Board findById(Long num) {
        Board board = boardRepository.findById(num).get();
        board.setHitCount(board.getHitCount()+1);
        boardRepository.save(board);
        return board;
    }

    @Override
    public void update(Board board) {
        Board b = boardRepository.findById(board.getBno()).get();
        b.setContent(board.getContent());
        b.setTitle(board.getTitle());
    }

    @Override
    public void delete(Long num) {
        boardRepository.deleteById(num);
    }*/


   /* //윤요섭쌤 참조
    @Override
    public Long register(BoardDTO boardDTO) {
        return 0L;
    }

    @Override
    public BoardDTO readOne(Long bno) {
        return null;
    }

    @Override
    public void modify(BoardDTO boardDTO) {

    }

    @Override
    public void remove(Long bno) {

    }

    @Override
    public PageResponseDTO<BoardDTO> list(PageRequestDTO pageRequestDTO) {
        return null;
    }

    @Override
    public PageResponseDTO<BoardListReplyCountDTO> listWithReplyCount(PageRequestDTO pageRequestDTO) {
        return null;
    }*/
}
