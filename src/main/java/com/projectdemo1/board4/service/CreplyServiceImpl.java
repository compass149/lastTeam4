package com.projectdemo1.board4.service;

import com.projectdemo1.board4.domain.Cboard;
import com.projectdemo1.board4.domain.Creply;
import com.projectdemo1.board4.repository.CboardRepository;
import com.projectdemo1.board4.repository.CreplyRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

@Transactional
@Log4j2
@RequiredArgsConstructor
@Service
public class CreplyServiceImpl implements CreplyService{

  private final CreplyRepository creplyRepository;
    private final CboardRepository cboardRepository;

@Override
public Creply registerCreply(Long bno, Creply creplyDTO) {
    Cboard cboard = cboardRepository.findById(bno).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다."));

    Creply creply = new Creply();
    creply.setReplyText(creplyDTO.getReplyText());
    creply.setReplyer(creplyDTO.getReplyer());
    creply.setCboard(cboard);

    if (creplyDTO.getParentId()!=null) {
        Creply parentCreply = creplyRepository
                .findById(creplyDTO.getParentId()).orElseThrow(()
                        -> new IllegalArgumentException("해당 댓글이 없습니다."));
        creply.setParent(parentCreply);
    }

    return creplyRepository.save(creply);


}

    @Override
    public List<Creply> getCreplies(Long bno) {
       Cboard cboard = cboardRepository.findById(bno)
               .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다."));

         return creplyRepository.findByCboardAndParentIsNull(cboard);
    }

    @Override
    public void deleteCreply(Long rno) {
        creplyRepository.deleteById(rno);

    }
}
