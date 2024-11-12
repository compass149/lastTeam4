//package com.projectdemo1.controller;
//
//import com.projectdemo1.domain.Post;
//import com.projectdemo1.domain.User;
//import com.projectdemo1.repository.PostRepository;
//import com.projectdemo1.repository.UserRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.core.annotation.AuthenticationPrincipal;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@Controller
//@RequiredArgsConstructor
//@RequestMapping("/user") // 클래스 레벨에서 /user로 설정
//public class UserController {
//    private final UserRepository userRepository;
//    private final PostRepository postRepository;
//    private final BCryptPasswordEncoder bCryptPasswordEncoder;
//
//    @GetMapping("/join")
//    public void join() {
//        // 회원가입 페이지로 이동
//    }
//
//    @PostMapping("/join")
//    public String register(User user) {
//        String rawPassword = user.getPassword();
//        String encPassword = bCryptPasswordEncoder.encode(rawPassword);
//        user.setPassword(encPassword);
//        user.setRole("USER"); // USER 권한 부여
//        userRepository.save(user);
//        return "redirect:/user/login"; // 회원가입 후 로그인 페이지로 이동
//    }
//
//    @GetMapping("/login")
//    public String login() {
//        return "user/login"; // 로그인 페이지로 이동
//    }
//
//    @GetMapping("/edit-profile") // 프로필 수정 페이지
//    public String editProfile(@AuthenticationPrincipal UserDetails userDetails, Model model) {
//        User user = userRepository.findByUsername(userDetails.getUsername());
//        model.addAttribute("user", user);
//        return "user/edit-profile"; // 프로필 수정 페이지로 이동
//    }
//
//    @GetMapping("/home") // 홈 화면
//    public String home() {
//        return "home"; // 홈 페이지로 이동
//    }
//
//    @GetMapping("/my-posts") // 내가 작성한 글 보기
//    public String viewMyPosts(@AuthenticationPrincipal UserDetails userDetails, Model model) {
//        User user = userRepository.findByUsername(userDetails.getUsername());
//        List<Post> posts = postRepository.findByUser(user);
//        model.addAttribute("posts", posts);
//        return "user/my-posts"; // 내 게시글 페이지로 이동
//    }
//
//    @GetMapping("/delete-account") // 계정 삭제 페이지
//    public String deleteAccountPage(@AuthenticationPrincipal UserDetails userDetails, Model model) {
//        User user = userRepository.findByUsername(userDetails.getUsername());
//        model.addAttribute("username", user.getNickname());
//        return "user/delete-account"; // 계정 삭제 페이지로 이동
//    }
//
//    @PostMapping("/delete-account") // 계정 삭제
//    public String deleteAccount(@AuthenticationPrincipal UserDetails userDetails) {
//        User user = userRepository.findByUsername(userDetails.getUsername());
//        userRepository.delete(user);
//        return "redirect:/logout"; // 로그아웃으로 리디렉션
//    }
//
//    @GetMapping("/edit-post/{id}") // 게시글 수정 페이지
//    public String editPostPage(@PathVariable Long id, @AuthenticationPrincipal UserDetails userDetails, Model model) {
//        User user = userRepository.findByUsername(userDetails.getUsername());
//        Post post = postRepository.findById(id).orElse(null);
//
//        if (post != null && post.getUser().getUno().equals(user.getUno())) {
//            model.addAttribute("post", post);
//            return "user/edit-post"; // 게시글 수정 페이지로 이동
//        }
//        return "redirect:/user/my-posts"; // 게시글이 없거나 권한이 없을 경우 리디렉션
//    }
//
//    @PostMapping("/edit-post/{id}") // 게시글 수정 기능
//    public String editPost(@PathVariable Long id, @AuthenticationPrincipal UserDetails userDetails, @ModelAttribute Post post) {
//        User user = userRepository.findByUsername(userDetails.getUsername());
//        Post existingPost = postRepository.findById(id).orElse(null);
//
//        if (existingPost != null && existingPost.getUser().getUno().equals(user.getUno())) {
//            existingPost.setTitle(post.getTitle());
//            existingPost.setContent(post.getContent());
//            postRepository.save(existingPost);
//        }
//        return "redirect:/user/my-posts"; // 수정 후 내 게시글 페이지로 리디렉션
//    }
//
//    @PostMapping("/delete-post/{id}") // 게시글 삭제 기능
//    public String deletePost(@PathVariable Long id, @AuthenticationPrincipal UserDetails userDetails) {
//        User user = userRepository.findByUsername(userDetails.getUsername());
//        Post post = postRepository.findById(id).orElse(null);
//
//        if (post != null && post.getUser().getUno().equals(user.getUno())) {
//            postRepository.delete(post);
//        }
//        return "redirect:/user/my-posts"; // 삭제 후 내 게시글 페이지로 리디렉션
//    }
//}
//
package com.projectdemo1.controller;

import com.projectdemo1.domain.Post;
import com.projectdemo1.domain.User;
import com.projectdemo1.repository.PostRepository;
import com.projectdemo1.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user") // 클래스 레벨에서 /user로 설정
public class UserController {
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @GetMapping("/join")
    public void join() {
        // 회원가입 페이지로 이동
    }

    @PostMapping("/join")
    public String register(User user) {
        String rawPassword = user.getPassword();
        String encPassword = bCryptPasswordEncoder.encode(rawPassword);
        user.setPassword(encPassword);
        user.setRole("USER"); // USER 권한 부여
        userRepository.save(user);
        return "redirect:/user/login"; // 회원가입 후 로그인 페이지로 이동
    }

    @GetMapping("/login")
    public String login() {
        return "user/login"; // 로그인 페이지로 이동
    }

    @GetMapping("/edit-profile") // 프로필 수정 페이지
    public String editProfile(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        User user = userRepository.findByUsername(userDetails.getUsername());
        model.addAttribute("user", user);
        return "user/edit-profile"; // 프로필 수정 페이지로 이동
    }

    @GetMapping("/home") // 홈 화면
    public String home() {
        return "home"; // 홈 페이지로 이동
    }

    @GetMapping("/my-posts") // 내가 작성한 글 보기
    public String viewMyPosts(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        User user = userRepository.findByUsername(userDetails.getUsername());
        List<Post> posts = postRepository.findByUser(user);
        model.addAttribute("posts", posts);
        return "user/my-posts"; // 내 게시글 페이지로 이동
    }

    @GetMapping("/delete-account") // 계정 삭제 페이지
    public String deleteAccountPage(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        User user = userRepository.findByUsername(userDetails.getUsername());
        model.addAttribute("username", user.getNickname());
        return "user/delete-account"; // 계정 삭제 페이지로 이동
    }

    @PostMapping("/delete-account") // 계정 삭제
    public String deleteAccount(@AuthenticationPrincipal UserDetails userDetails) {
        User user = userRepository.findByUsername(userDetails.getUsername());
        userRepository.delete(user);
        return "redirect:/logout"; // 로그아웃으로 리디렉션
    }

    @GetMapping("/edit-post/{id}") // 게시글 수정 페이지
    public String editPostPage(@PathVariable Long id, @AuthenticationPrincipal UserDetails userDetails, Model model) {
        User user = userRepository.findByUsername(userDetails.getUsername());
        Post post = postRepository.findById(id).orElse(null);

        if (post != null && post.getUser().getUno().equals(user.getUno())) {
            model.addAttribute("post", post);
            return "user/edit-post"; // 게시글 수정 페이지로 이동
        }
        return "redirect:/user/my-posts"; // 게시글이 없거나 권한이 없을 경우 리디렉션
    }

    @PostMapping("/edit-post/{id}") // 게시글 수정 기능
    public String editPost(@PathVariable Long id, @AuthenticationPrincipal UserDetails userDetails, @ModelAttribute Post post) {
        User user = userRepository.findByUsername(userDetails.getUsername());
        Post existingPost = postRepository.findById(id).orElse(null);

        if (existingPost != null && existingPost.getUser().getUno().equals(user.getUno())) {
            existingPost.setTitle(post.getTitle());
            existingPost.setContent(post.getContent());
            postRepository.save(existingPost);
        }
        return "redirect:/user/my-posts"; // 수정 후 내 게시글 페이지로 리디렉션
    }

    @PostMapping("/delete-post/{id}") // 게시글 삭제 기능
    public String deletePost(@PathVariable Long id, @AuthenticationPrincipal UserDetails userDetails) {
        User user = userRepository.findByUsername(userDetails.getUsername());
        Post post = postRepository.findById(id).orElse(null);

        if (post != null && post.getUser().getUno().equals(user.getUno())) {
            postRepository.delete(post);
        }
        return "redirect:/user/my-posts"; // 삭제 후 내 게시글 페이지로 리디렉션
    }

    // 아이디 중복 체크
    @GetMapping("/check-userId")
    @ResponseBody
    public Map<String, Object> checkUserId(@RequestParam String userId) {
        Map<String, Object> response = new HashMap<>();
        boolean isAvailable = userRepository.findByUsername(userId) == null; // 아이디 중복 체크
        response.put("available", isAvailable);
        return response;
    }

    // 닉네임 중복 체크
    @GetMapping("/check-nickname")
    @ResponseBody
    public Map<String, Object> checkNickname(@RequestParam String nickname) {
        Map<String, Object> response = new HashMap<>();
        boolean isAvailable = userRepository.findByNickname(nickname) == null; // 닉네임 중복 체크
        response.put("available", isAvailable);
        return response;
    }
    @GetMapping("/admin/list")
    public String adminList() {
        return "admin/list";
    }
}
