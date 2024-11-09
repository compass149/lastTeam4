/*  박경미 쌤 코드
package com.projectdemo1.controller;

import com.projectdemo1.auth.PrincipalDetails;
import com.projectdemo1.domain.Board;
import com.projectdemo1.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/board")

@Log4j2
@RequiredArgsConstructor
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
        model.addAttribute("board", boardService.(num));
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
*/
package com.projectdemo1.controller;

import com.projectdemo1.dto.BoardDTO;
import com.projectdemo1.dto.BoardListReplyCountDTO;
import com.projectdemo1.dto.PageRequestDTO;
import com.projectdemo1.dto.PageResponseDTO;
import com.projectdemo1.service.BoardService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/board")
@Log4j2
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @GetMapping("/list")
    public void list(PageRequestDTO pageRequestDTO, Model model){

        // PageResponseDTO<BoardDTO> responseDTO = boardService.list(pageRequestDTO);
        PageResponseDTO<BoardListReplyCountDTO> responseDTO = boardService.listWithReplyCount(pageRequestDTO);

        log.info(responseDTO);

        model.addAttribute("responseDTO", responseDTO);

    }

    @GetMapping("/register")
    public void registerGET(){

    }

    @PostMapping("/register")
    public String registerPost(@Valid BoardDTO boardDTO, BindingResult bindingResult, RedirectAttributes redirectAttributes){

        log.info("board POST register.......");

        if(bindingResult.hasErrors()) {
            log.info("has errors.......");
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors() );
            return "redirect:/board/register";
        }

        log.info(boardDTO);

        Long bno  = boardService.register(boardDTO);

        redirectAttributes.addFlashAttribute("result", bno);

        return "redirect:/board/list";
    }


//    @GetMapping("/read")
//    public void read(Long bno, PageRequestDTO pageRequestDTO, Model model){
//
//        BoardDTO boardDTO = boardService.readOne(bno);
//
//        log.info(boardDTO);
//
//        model.addAttribute("dto", boardDTO);
//
//    }


    @GetMapping({"/read", "/modify"})
    public void read(Long bno, PageRequestDTO pageRequestDTO, Model model){

        BoardDTO boardDTO = boardService.readOne(bno);

        log.info(boardDTO);

        model.addAttribute("dto", boardDTO);

    }

    @PostMapping("/modify")
    public String modify( PageRequestDTO pageRequestDTO,
                          @Valid BoardDTO boardDTO,
                          BindingResult bindingResult,
                          RedirectAttributes redirectAttributes){

        log.info("board modify post......." + boardDTO);

        if(bindingResult.hasErrors()) {
            log.info("has errors.......");

            String link = pageRequestDTO.getLink();

            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors() );

            redirectAttributes.addAttribute("bno", boardDTO.getBno());

            return "redirect:/board/modify?"+link;
        }

        boardService.modify(boardDTO);

        redirectAttributes.addFlashAttribute("result", "modified");

        redirectAttributes.addAttribute("bno", boardDTO.getBno());

        return "redirect:/board/read";
    }


    @PostMapping("/remove")
    public String remove(Long bno, RedirectAttributes redirectAttributes) {

        log.info("remove post.. " + bno);

        boardService.remove(bno);

        redirectAttributes.addFlashAttribute("result", "removed");

        return "redirect:/board/list";

    }
}
