package com.projectdemo1.board4.repository;


import com.projectdemo1.board4.domain.Cboard;
import com.projectdemo1.board4.domain.Creply;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CreplyRepository extends JpaRepository<Creply, Long> {
    @Query("select r from Creply r where r.cboard.cno = :rno")
    Page<Creply> listOfCboard(Long rno, Pageable pageable);

    void deleteByCboardCno(Long rno);

}
