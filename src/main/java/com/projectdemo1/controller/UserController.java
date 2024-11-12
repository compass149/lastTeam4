package com.projectdemo1.controller;

import com.projectdemo1.domain.Post;
import com.projectdemo1.domain.User;
import com.projectdemo1.repository.PostRepository;
import com.projectdemo1.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user") // 클래스 레벨에서 /user로 설정

public class UserController {
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @GetMapping("/join")
    public void join(){
    }

    @PostMapping("/join")
    public String register(User user){
        System.out.println("register user: " + user);
        String rawPassword = user.getPassword();
        String encPassword = bCryptPasswordEncoder.encode(rawPassword);
        user.setPassword(encPassword);
        user.setRole("USER"); // USER라는 role을 부여해서 user만 접근 가능하게
        userRepository.save(user);
        return "redirect:/user/login"; // 회원가입 후 로그인 페이지로 이동
    }

 /*   @GetMapping("/login")
    public void login(){
    }*/

    @GetMapping("/login")
    public String login(){
        return "user/login";
    }

    @GetMapping("/edit-profile") // /user/edit-profile로 매핑
    public String editProfile(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        // 로그인한 사용자의 username을 통해 DB에서 User 엔티티를 조회
        User user = userRepository.findByUsername(userDetails.getUsername());

        // 조회한 사용자 정보를 모델에 추가
        model.addAttribute("user", user);
        return "user/edit-profile"; // 'edit-profile.html' 파일 경로 반환
    }

    @GetMapping("home") // 홈화면
    public String home() {
        return "home";
    }

    // "내가 작성한 글 보기" 매핑 추가
    @GetMapping("/my-posts") // "/user/my-posts"로 매핑
    public String viewMyPosts(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        User user = userRepository.findByUsername(userDetails.getUsername());
        List<Post> posts = postRepository.findByUser(user); // 작성한 글 리스트 조회
        model.addAttribute("posts", posts);
        return "user/my-posts"; // my-posts.html 템플릿 반환
    }

    @GetMapping("/delete-account")
    public String deleteAccountPage(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        User user = userRepository.findByUsername(userDetails.getUsername());
        model.addAttribute("username", user.getNickname());
        return "user/delete-account";
    }

    @PostMapping("/delete-account")
    public String deleteAccount(@AuthenticationPrincipal UserDetails userDetails) {
        User user = userRepository.findByUsername(userDetails.getUsername());
        userRepository.delete(user); // 사용자 삭제
        return "redirect:/logout"; // 로그아웃으로 리디렉션
    }

    // 게시글 수정 페이지로 이동
    @GetMapping("/edit-post/{id}")
    public String editPostPage(@PathVariable Long id, @AuthenticationPrincipal UserDetails userDetails, Model model) {
        User user = userRepository.findByUsername(userDetails.getUsername());
        Post post = postRepository.findById(id).orElse(null);

        if (post != null && post.getUser().getUno().equals(user.getUno())) {
            model.addAttribute("post", post);
            return "user/edit-post";
        }
        return "redirect:/user/my-posts"; // 게시글이 없거나 권한이 없을 경우 리디렉션
    }

    // 게시글 수정 기능
    @PostMapping("/edit-post/{id}")
    public String editPost(@PathVariable Long id, @AuthenticationPrincipal UserDetails userDetails, @ModelAttribute Post post) {
        User user = userRepository.findByUsername(userDetails.getUsername());
        Post existingPost = postRepository.findById(id).orElse(null);

        if (existingPost != null && existingPost.getUser().getUno().equals(user.getUno())) {
            existingPost.setTitle(post.getTitle());
            existingPost.setContent(post.getContent());
            postRepository.save(existingPost);
        }
        return "redirect:/user/my-posts";
    }

    // 게시글 삭제 기능
    @PostMapping("/delete-post/{id}")
    public String deletePost(@PathVariable Long id, @AuthenticationPrincipal UserDetails userDetails) {
        User user = userRepository.findByUsername(userDetails.getUsername());
        Post post = postRepository.findById(id).orElse(null);

        if (post != null && post.getUser().getUno().equals(user.getUno())) {
            postRepository.delete(post);
        }
        return "redirect:/user/my-posts";
    }
}
