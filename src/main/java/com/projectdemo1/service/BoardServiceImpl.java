package com.projectdemo1.service;

import com.projectdemo1.domain.Board;
import com.projectdemo1.domain.User;
import com.projectdemo1.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;

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
        board.setHitcount(board.getHitcount()+1);
        boardRepository.save(board);
        return board;
    }

    @Override
    public void update(Board board) {
        Board b = boardRepository.findById(board.getNum()).get();
        b.setContent(board.getContent());
        b.setTitle(board.getTitle());
    }

    @Override
    public void delete(Long num) {
        boardRepository.deleteById(num);
    }
}
