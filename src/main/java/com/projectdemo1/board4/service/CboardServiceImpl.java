package com.projectdemo1.board4.service;


import com.projectdemo1.board4.domain.Cboard;
import com.projectdemo1.board4.repository.CboardRepository;
import com.projectdemo1.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CboardServiceImpl implements CboardService{

    private final CboardRepository cboardRepository;

    @Override
    public void register(Cboard cboard, User user) {
        cboard.setUser(user);
        cboardRepository.save(cboard);
    }

    @Override
    public List<Cboard> list() {
        return cboardRepository.findAll();
    }

    @Override
    public Cboard findById(Long bno) {
        Cboard cboard = cboardRepository.findById(bno).get();
        cboard.setHitCount(cboard.getHitCount() + 1);
        cboardRepository.save(cboard);
        return cboard;
    }

    @Override
    public void modify(Cboard cboard) {
        Cboard c = cboardRepository.findById(cboard.getCno()).get();
        c.setContent(cboard.getContent());
        c.setTitle(cboard.getTitle());
    }

    @Override
    public void remove(Long bno) {
        cboardRepository.deleteById(bno);

    }
}
