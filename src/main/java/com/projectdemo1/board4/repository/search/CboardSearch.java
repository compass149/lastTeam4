package com.projectdemo1.board4.repository.search;


import com.projectdemo1.board4.domain.Cboard;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CboardSearch {

    Page<Cboard> searchAll(String[] type, String keyword, Pageable pageable);
}
