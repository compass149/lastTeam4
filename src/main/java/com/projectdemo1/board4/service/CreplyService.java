package com.projectdemo1.board4.service;



import com.projectdemo1.board4.domain.Creply;
import com.projectdemo1.board4.dto.CpageRequestDTO;
import com.projectdemo1.board4.dto.CpageResponseDTO;
import com.projectdemo1.board4.dto.CreplyDTO;

import java.util.List;

public interface CreplyService {

    Long register(CreplyDTO dto);

    CreplyDTO read(Long rno);


    void modify(CreplyDTO dto);

    void remove(Long rno);

    CpageResponseDTO<CreplyDTO> getListOfCboard(Long cno, CpageRequestDTO requestDTO);

}
