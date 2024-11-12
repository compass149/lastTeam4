package com.projectdemo1.controller;



import com.projectdemo1.domain.Comment;
import com.projectdemo1.domain.Post;
import com.projectdemo1.domain.User;
import com.projectdemo1.repository.PostRepository;
import com.projectdemo1.repository.UserRepository;
import com.projectdemo1.service.CommentService;
import com.projectdemo1.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user") // 클래스 레벨에서 /user로 설정
public class UserController {
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final CommentService commentService;
    private final UserService userService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    // 사용자 등록 (회원가입)
    @GetMapping("/join")
    public void join() {
    }

    @PostMapping("/join")
    public String register(User user) {
        String rawPassword = user.getPassword();
        String encPassword = bCryptPasswordEncoder.encode(rawPassword);
        user.setPassword(encPassword);
        user.setRole("USER");
        userRepository.save(user);
        return "redirect:/user/login"; // 회원가입 후 로그인 페이지로 이동
    }

    // 로그인 페이지
    @GetMapping("/login")
    public void login() {
    }

    // 프로필 수정 페이지
    @GetMapping("/edit-profile")
    public String editProfile(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        User user = userRepository.findByUsername(userDetails.getUsername());
        model.addAttribute("user", user);
        return "user/edit-profile"; // 'edit-profile.html' 파일 경로 반환
    }

    // 홈 화면
    @GetMapping("/home")
    public String home() {
        return "home";
    }
    @GetMapping("/list")
    public String list() {
        return "admin/adminboard"; // adminboard.html을 반환
    }
    // 내가 작성한 글 보기
    @GetMapping("/my-posts")
    public String viewMyPosts(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        User user = userRepository.findByUsername(userDetails.getUsername());
        List<Post> posts = postRepository.findByUser(user);
        model.addAttribute("posts", posts);
        return "user/my-posts"; // my-posts.html 템플릿 반환
    }

    // 댓글 목록 조회
    @GetMapping("/comments/{bno}")
    @ResponseBody
    public List<Comment> listComments(@PathVariable Long bno) {
        return commentService.listByBoard(bno);
    }

    // 댓글 등록
    @PostMapping("/comments")
    @ResponseBody
    public void registerComment(@RequestBody Comment comment) {
        commentService.register(comment);
    }

    // 댓글 수정
    @PutMapping("/comments/{cno}")
    @ResponseBody
    public void modifyComment(@PathVariable Long cno, @RequestBody Comment comment) {
        comment.setCno(cno);
        commentService.modify(comment);
    }

    // 댓글 삭제
    @DeleteMapping("/comments/{cno}")
    @ResponseBody
    public void removeComment(@PathVariable Long cno) {
        commentService.remove(cno);
    }

    // 계정 삭제 페이지
    @GetMapping("/delete-account")
    public String deleteAccountPage(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        User user = userRepository.findByUsername(userDetails.getUsername());
        model.addAttribute("username", user.getNickname());
        return "user/delete-account";
    }

    // 계정 삭제
    @PostMapping("/delete-account")
    public String deleteAccount(@AuthenticationPrincipal UserDetails userDetails) {
        User user = userRepository.findByUsername(userDetails.getUsername());
        userRepository.delete(user);
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

    // 아이디 중복 확인
    @GetMapping("/check-userId")
    public Map<String, Boolean> checkUserId(@RequestParam String userId) {
        boolean available = userService.isUserIdAvailable(userId);
        return Collections.singletonMap("available", available);
    }

    // 닉네임 중복 확인
    @GetMapping("/check-nickname")
    public Map<String, Boolean> checkNickname(@RequestParam String nickname) {
        boolean available = userService.isNicknameAvailable(nickname);
        return Collections.singletonMap("available", available);
    }
}
