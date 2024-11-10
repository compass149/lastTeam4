package com.projectdemo1.service;


import com.projectdemo1.board4.domain.Cboard;
import com.projectdemo1.board4.dto.CboardDTO;
import com.projectdemo1.board4.dto.CpageRequestDTO;
import com.projectdemo1.board4.dto.CpageResponseDTO;
import com.projectdemo1.domain.Board;
import com.projectdemo1.domain.User;
import com.projectdemo1.dto.BoardDTO;
import com.projectdemo1.dto.BoardListReplyCountDTO;
import com.projectdemo1.dto.PageRequestDTO;
import com.projectdemo1.dto.PageResponseDTO;

import java.util.List;
import java.util.stream.Collectors;

public interface BoardService {

void register(Board board, User user);
    List<Board> list();
    Board findById(Long bno);
    void modify(Board board);
    void remove(Long bno);

    PageResponseDTO<BoardDTO> list(PageRequestDTO pageRequestDTO);

    default Board dtoToEntity(BoardDTO boardDTO) {
        Board board = Board.builder()
                .bno(boardDTO.getBno())
                .title(boardDTO.getTitle())
                .content(boardDTO.getContent())
                .writer(boardDTO.getWriter())
                .build();
        if(boardDTO.getFileNames()!=null){
            boardDTO.getFileNames().forEach(fileName -> {
                String[] arr = fileName.split("_");
                board.addImage(arr[0], arr[1]);
            });
        }
        return board;
    }
    default BoardDTO entityToDTO(Board board) {
        BoardDTO boardDTO = BoardDTO.builder()
                .bno(board.getBno())
                .title(board.getTitle())
                .content(board.getContent())
                .writer(board.getWriter())
                .regDate(board.getCreatedAt())
                .modDate(board.getUpdatedAt())
                .build();

        List<String> fileNames = board.getImageSet().stream()
                .sorted().map(boardImage -> boardImage.getUuid()+"_"+boardImage.getFileName())
                .collect(Collectors.toList());
        boardDTO.setFileNames(fileNames);
        return boardDTO;
    }
}
