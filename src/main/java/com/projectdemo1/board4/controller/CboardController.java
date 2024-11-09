package com.projectdemo1.board4.controller;


import com.projectdemo1.auth.PrincipalDetails;
import com.projectdemo1.board4.domain.Cboard;

import com.projectdemo1.board4.dto.CboardDTO;
import com.projectdemo1.board4.service.CboardService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@RequestMapping("/cboard")
@Log4j2
@RequiredArgsConstructor
public class CboardController {

    @Autowired
    private CboardService cboardService;

    @Value("${com.projectdemo1.board4.upload.path}")
    private String uploadPath; // 업로드 경로


    @GetMapping("/cregister") //게시글 등록
    public String register() {
        return "/cboard/cregister";
    }

    @PostMapping("/cregister")
    public String register(Cboard cboard, @AuthenticationPrincipal PrincipalDetails principal) {
        cboardService.register(cboard, principal.getUser());
        return "redirect:/cboard/clist";
    }

    @GetMapping("/cread")
    public String read(@RequestParam("cno") Long cno, Model model) {
        model.addAttribute("cboard", cboardService.findById(cno));
        return "/cboard/cread";
    }

    @GetMapping("/cmodify")
    public String modify(@RequestParam Long cno, Model model) {
        model.addAttribute("cboard", cboardService.findById(cno));
        return "/cboard/cmodify";
    }

    @PostMapping("/cmodify")
    public String modify(Cboard cboard) {
        cboardService.modify(cboard);
        return "redirect:/cboard/cread?cno=" + cboard.getCno();
    }

    @GetMapping("/clist")
    public String list(Model model) {
        List<Cboard> clists = cboardService.list();
        model.addAttribute("clists", clists);
        return "/cboard/clist";
    }

    @PostMapping("/cremove")
    public String remove(@RequestParam Long cno) {

        cboardService.remove(cno);
        return "redirect:/cboard/clist";
    }
}

