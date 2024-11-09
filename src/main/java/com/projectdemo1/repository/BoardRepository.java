package com.projectdemo1.repository;


import com.projectdemo1.domain.Board;
import com.projectdemo1.repository.search.BoardSearch;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long>,  BoardSearch {

}
