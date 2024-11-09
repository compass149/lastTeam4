package com.projectdemo1.board4.controller;


import com.projectdemo1.board4.domain.Creply;
import com.projectdemo1.board4.dto.CreplyDTO;
import com.projectdemo1.board4.service.CreplyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/creplies")
@Log4j2
@RequiredArgsConstructor
public class CreplyController {
    private final CreplyService creplyService;

    @PostMapping("/{cno}")
    public ResponseEntity<Long> registerCreply(
            @PathVariable Long cno,
            @RequestBody CreplyDTO creplyDTO) {
        log.debug("Received request to register reply for cno: {}", cno);
        log.debug("CreplyDTO: {}", creplyDTO);
        Creply creply = creplyService.registerCreply(cno, creplyDTO);
        log.debug("Saved Creply: {}", creply);
        return ResponseEntity.ok(creply.getRno());
    }

    @GetMapping("/{cno}")
    public ResponseEntity<List<Creply>> getCreplies(@PathVariable Long cno) {
        log.debug("Received request to get replies for cno: {}", cno);
        List<Creply> creplies = creplyService.getCreplies(cno);
        log.debug("Retrieved Creplies: {}", creplies);
        return ResponseEntity.ok(creplies);
    }

    @DeleteMapping("/{rno}")
    public ResponseEntity<String> deleteCreply(@PathVariable("rno") Long rno) {
        creplyService.deleteCreply(rno);
        return ResponseEntity.ok("success");
    }




}
