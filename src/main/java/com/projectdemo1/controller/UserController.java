package com.projectdemo1.controller;

import com.projectdemo1.domain.User;
import com.projectdemo1.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
public class UserController {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @GetMapping("/user/join")
    public void join(){
    }

    @PostMapping("/user/join")
    public String register(User user){
        System.out.println("register user: " + user);
        String rawPassword = user.getPassword();
        String encPassword = bCryptPasswordEncoder.encode(rawPassword);
        user.setPassword(encPassword);
        user.setRole("USER"); // USER라는 role을 부여해서 user만 접근 가능하게
        userRepository.save(user);
        return "redirect:/user/login"; // 회원가입 후 로그인 페이지로 이동
    }

    @GetMapping("/user/login")
    public void login(){
    }
    @GetMapping("/home") //홈화면
    public String home(){
        return "home";
    }
}

