package com.projectdemo1.controller;

import com.projectdemo1.domain.Post;
import com.projectdemo1.domain.User;
import com.projectdemo1.repository.PostRepository;
import com.projectdemo1.repository.UserRepository;
import com.projectdemo1.service.UserService;
import com.projectdemo1.service.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@Log4j2

public class UserController {
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserServiceImpl userServiceImpl;

    @GetMapping("user/join")
    public void join() {
    }

    @PostMapping("/user/join")
    public String register(User user, BindingResult bindingResult){
        System.out.println("register user: " + user);
        String rawPassword = user.getPassword();
        String encPassword = bCryptPasswordEncoder.encode(rawPassword);
        user.setPassword(encPassword);
        user.setRole("USER"); // USER라는 role을 부여해서 user만 접근 가능하게
        if (bindingResult.hasErrors()) {
            return "user/join";  // 폼 검증 에러가 있으면 회원가입 페이지로 돌아감
        }
        userRepository.save(user);
        return "redirect:/user/login"; // 회원가입 후 로그인 페이지로 이동
    }

    @GetMapping("/user/login")
    public String login(){
        return "user/login";
    }

    @GetMapping("/user/edit-profile") // /user/edit-profile로 매핑
    public String editProfile(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        // 로그인한 사용자의 username을 통해 DB에서 User 엔티티를 조회
        User user = userRepository.findByUsername(userDetails.getUsername());

        // 조회한 사용자 정보를 모델에 추가
        model.addAttribute("user", user);
        return "user/edit-profile"; // 'edit-profile.html' 파일 경로 반환
    }
    @PostMapping("/user/edit-profile")
    public String editProfile(@AuthenticationPrincipal UserDetails userDetails, Model model, User user){
        String rawPassword = user.getPassword();
        String encPassword = bCryptPasswordEncoder.encode(rawPassword);
        user.setPassword(encPassword);
        userRepository.save(user);
        System.out.println("수정완료" + user);
        return "redirect:/user/edit-profile";
    }

    @GetMapping("/home") // 홈화면
    public String home() {
        return "home";
    }

    // "내가 작성한 글 보기" 매핑 추가
    @GetMapping("/user/my-posts") // "/user/my-posts"로 매핑
    public String viewMyPosts(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        User user = userRepository.findByUsername(userDetails.getUsername());
        List<Post> posts = postRepository.findByUser(user); // 작성한 글 리스트 조회
        model.addAttribute("posts", posts);
        return "user/my-posts"; // my-posts.html 템플릿 반환
    }

    @GetMapping("/user/delete-account")
    public String deleteAccountPage(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        User user = userRepository.findByUsername(userDetails.getUsername());
        model.addAttribute("username", user.getNickname());
        return "user/delete-account";
    }


    @PostMapping("/user/delete-account")
    public String deleteAccount() {
        userServiceImpl.deleteAccount(); // UserServiceImpl의 deleteAccount 메서드를 호출
        return "redirect:/logout"; // 삭제 후 로그아웃으로 리디렉션
    }

    // 게시글 수정 페이지로 이동
    @GetMapping("/user/edit-post/{id}")
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
    @PostMapping("/user/edit-post/{id}")
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
    @PostMapping("/user/delete-post/{id}")
    public String deletePost(@PathVariable Long id, @AuthenticationPrincipal UserDetails userDetails) {
        User user = userRepository.findByUsername(userDetails.getUsername());
        Post post = postRepository.findById(id).orElse(null);

        if (post != null && post.getUser().getUno().equals(user.getUno())) {
            postRepository.delete(post);
        }
        return "redirect:/user/my-posts"; // 삭제 후 내 게시글 페이지로 리디렉션
    }

    // 아이디 중복 체크 동작 안 됨
    @GetMapping("/check-username")
    @ResponseBody
    public Map<String, Object> checkUserId(@RequestParam String username) {
        Map<String, Object> response = new HashMap<>();
        boolean isAvailable = userRepository.findByUsername(username) == null; // 아이디 중복 체크
        response.put("available", isAvailable);
        return response;
    }

    // 닉네임 중복 체크 //동작 안 됨
    @GetMapping("/check-nickname")
    @ResponseBody
    public Map<String, Object> checkNickname(@RequestParam String nickname) {
        Map<String, Object> response = new HashMap<>();
        boolean isAvailable = userRepository.findByNickname(nickname) == null; // 닉네임 중복 체크
        response.put("available", isAvailable);
        return response;
    }
        @GetMapping("/admin/list") // "/user/my-posts"로 매핑
    public String adminList(@AuthenticationPrincipal UserDetails userDetails, Model model) {
            List<User> users = userRepository.findAll();

            // 모든 사용자 정보를 모델에 추가
            model.addAttribute("users", users);

        return "admin/list"; // my-posts.html 템플릿 반환
    }
}
