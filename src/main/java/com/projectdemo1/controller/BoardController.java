package com.projectdemo1.controller;

import com.projectdemo1.auth.PrincipalDetails;
import com.projectdemo1.domain.Board;
import com.projectdemo1.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/board")
public class BoardController {
    @Autowired
    private BoardService boardService;

    @GetMapping("/insert")
    public String insert() {
        return "board/join";
    }
    @PostMapping("/insert")
    public String insert(Board board, @AuthenticationPrincipal PrincipalDetails principalDetails) {
        boardService.insert(board, principalDetails.getUser()); //principalDetails를 통해 로그인한 사용자 정보를 가져옴(권한 정보 포함)
        return "redirect:/board/list";
    }

    @GetMapping("/view")
    public String view(@RequestParam("num") Long num, Model model) {
        model.addAttribute("board", boardService.findById(num));
        return "/board/view";
    }
    //수정폼
    @GetMapping("/modify")
    public String update(@RequestParam Long num, Model model) {
        model.addAttribute("board", boardService.findById(num));
        return "/board/modify";
    }

    @GetMapping("/list")
    public String list(Model model) {
        model.addAttribute("lists", boardService.list());
        return "/board/list";
    }
    @PostMapping("/update")
    public String update(Board board) {

        boardService.update(board);
        return "redirect:/board/view?num="+board.getBno();
    }
    @GetMapping("/delete")
    public String delete(@RequestParam Long num) {
        boardService.delete(num);
        return "redirect:/board/list";
    }
}
