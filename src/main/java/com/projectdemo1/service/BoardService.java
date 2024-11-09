package com.projectdemo1.service;


import com.projectdemo1.board4.domain.Cboard;
import com.projectdemo1.domain.Board;
import com.projectdemo1.domain.User;
import com.projectdemo1.dto.BoardDTO;
import com.projectdemo1.dto.BoardListReplyCountDTO;
import com.projectdemo1.dto.PageRequestDTO;
import com.projectdemo1.dto.PageResponseDTO;

import java.util.List;

public interface BoardService {
//박경미 쌤 참조
//    evoid insert(Board board, User user);
//    public List<Board> list();
//    public Board findById(Long bno);
//    public void update(Board board);
//    public void delete(Long num);
void register(Board board, User user);
    public List<Board> list();
    public Board findById(Long bno);
    public void modify(Board board);
    public void remove(Long bno);

}
/*

    //윤요섭쌤 참조
Long register(BoardDTO boardDTO);

    BoardDTO readOne(Long bno);

    void modify(BoardDTO boardDTO);

    void remove(Long bno);

    PageResponseDTO<BoardDTO> list(PageRequestDTO pageRequestDTO);

    //댓글의 숫자까지 처리
    PageResponseDTO<BoardListReplyCountDTO> listWithReplyCount(PageRequestDTO pageRequestDTO);
}*/
