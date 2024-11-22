package com.projectdemo1.auth;

import com.projectdemo1.domain.User;
import com.projectdemo1.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Log4j2
@RequiredArgsConstructor
public class PrincipalDetailService  implements UserDetailsService {

    private final UserRepository userRepository;

    /*@Override
    @Transactional

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("loadUserByUsername");
        User user=userRepository.findByUsername(username);
        log.info("user" + user);
        if(user==null) return null;
        PrincipalDetails puser=new PrincipalDetails(user);
        log.info(puser);
        return puser;*/
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("Attempting to load user by username: {}", username);

        // 사용자 검색
        User user = userRepository.findByUsername(username);
        if (user == null) {
            log.warn("User not found: {}", username);
            throw new UsernameNotFoundException("User not found with username: " + username);
        }

        // UserDetails 생성
        log.info("User found: {}", user);
        PrincipalDetails puser = new PrincipalDetails(user);
        log.info("PrincipalDetails created: {}", puser);

        return puser;
    }
}