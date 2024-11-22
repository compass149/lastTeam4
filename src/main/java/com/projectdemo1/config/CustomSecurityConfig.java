package com.projectdemo1.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@Log4j2
@EnableWebSecurity
@RequiredArgsConstructor
public class CustomSecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, UserDetailsService userDetailService) throws Exception {
        log.info("보안 설정 시작...");

        return http
                .csrf(httpSecurityCsrfConfigurer -> httpSecurityCsrfConfigurer.disable()) // CSRF 비활성화
                .authorizeHttpRequests(authorizeHttpRequestsConfigurer -> authorizeHttpRequestsConfigurer
                        .requestMatchers("/home", "/user/**").permitAll()  // 홈과 사용자 관련 페이지는 모두 접근 허용
                        .requestMatchers("/board/**", "/user/**", "/", "/home", "/cboard/**").permitAll()  // 특정 URL은 모두 접근 허용
                        .requestMatchers(HttpMethod.GET, "/board/**").permitAll()  // GET 요청에 대해서는 게시판 URL 접근 허용
                        .requestMatchers("/admin/**").hasAuthority("ADMIN")  // 관리자 페이지는 ADMIN 권한만 접근 허용
                        .requestMatchers("/creplies/**").authenticated()  // 댓글 관련 페이지는 로그인한 사용자만 접근 허용
                        .anyRequest().authenticated())  // 나머지 요청은 모두 인증 필요
                .formLogin(formLoginConfigurer -> formLoginConfigurer
                        .loginPage("/user/login")  // 로그인 페이지 경로 설정
                        .loginProcessingUrl("/user/login")  // 로그인 처리 URL
                        .usernameParameter("username")  // 사용자명 파라미터 이름
                        .passwordParameter("password")  // 비밀번호 파라미터 이름

                        .defaultSuccessUrl("/home", true)  // 로그인 성공 시 홈 페이지로 리디렉션
                        .failureUrl("/error")  // 로그인 실패 시 에러 페이지로 리디렉션
                        .permitAll())  // 로그인 페이지는 모든 사용자에게 허용

                .rememberMe(rememberMeConfigurer -> rememberMeConfigurer
                        .key("uniqueAndSecret")  // Remember-me 기능의 키
                        .tokenValiditySeconds(86400)  // Remember-me 유효 기간 (24시간)
                        .userDetailsService(userDetailService))  // 사용자 서비스 설정
                .logout(logoutConfigurer -> logoutConfigurer
                        .logoutUrl("/logout")  // 로그아웃 URL
                        .logoutSuccessUrl("/home")  // 로그아웃 후 홈 페이지로 리디렉션
                        .invalidateHttpSession(true)  // 세션 무효화
                        .clearAuthentication(true))  // 인증 정보 초기화
                .exceptionHandling(exceptionHandlingConfigurer -> exceptionHandlingConfigurer
                        .accessDeniedPage("/user/login"))  // 접근 거부 시 로그인 페이지로 리디렉션
                .build();

    }

    @Bean  // 정적 자원은 보안 필터 제외
    public WebSecurityCustomizer configure() {
        log.info("정적 자원에 대한 보안 필터 제외 설정");
        return (web) -> web.ignoring()  // 모든 정적 자원 필터 제외
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        log.info("BCryptPasswordEncoder Bean 생성");
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http, BCryptPasswordEncoder bCryptPasswordEncoder, UserDetailsService userDetailService)
            throws Exception {

        log.info("AuthenticationManager 생성 시작...");
        AuthenticationManagerBuilder authenticationManagerBuilder =
                http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(userDetailService).passwordEncoder(bCryptPasswordEncoder);
        log.info("AuthenticationManager 생성 완료");
        return authenticationManagerBuilder.build();
    }
}
