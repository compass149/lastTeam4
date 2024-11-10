package com.projectdemo1.board4.repository.search;


import com.projectdemo1.board4.domain.Cboard;
import com.projectdemo1.domain.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CboardSearch {

    Page<Board> search1(Pageable pageable);
    Page<Cboard> searchAll(String[] type, String keyword, Pageable pageable);
}
