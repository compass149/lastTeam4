package com.projectdemo1.board4.service;


import com.projectdemo1.board4.domain.Cboard;
import com.projectdemo1.board4.dto.CboardDTO;
import com.projectdemo1.board4.dto.CpageRequestDTO;
import com.projectdemo1.board4.dto.CpageResponseDTO;
import com.projectdemo1.domain.User;

import java.util.List;
import java.util.stream.Collectors;

public interface CboardService {
    void register(Cboard cboard, User user);
    public List<Cboard> list();
    public Cboard findById(Long bno);
    public void modify(Cboard cboard);
    public void remove(Long bno);

}