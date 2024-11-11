package com.projectdemo1.service;

import com.projectdemo1.domain.Post;
import com.projectdemo1.domain.User;
import com.projectdemo1.repository.PostRepository;
import com.projectdemo1.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PostRepository postRepository; // PostRepository 추가
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public void saveUser(User user){
        String rawPassword = user.getPassword();
        String encPassword = bCryptPasswordEncoder.encode(rawPassword);
        user.setPassword(encPassword);
        user.setRole("USER");
        userRepository.save(user);
    }

    // 게시물 수정 및 삭제 권한을 확인하는 메서드 추가
    public boolean hasPostPermission(Long postId, Long userId) {
        Optional<Post> optionalPost = postRepository.findById(postId);
        return optionalPost.isPresent() && optionalPost.get().getUser().getUno().equals(userId);
    }
}
