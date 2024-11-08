package com.projectdemo1.board4.service;



import com.projectdemo1.board4.domain.Creply;
import com.projectdemo1.board4.dto.CreplyDTO;

import java.util.List;

public interface CreplyService {

   public Creply registerCreply(Long bno, CreplyDTO creplyDTO);

   public List<Creply> getCreplies(Long bno);
   public void deleteCreply(Long rno);

}
