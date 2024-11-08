package com.projectdemo1.config;

import jakarta.servlet.DispatcherType;
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
@EnableWebSecurity //Spring Security 설정을 위한 어노테이션
@RequiredArgsConstructor //final로 선언된 필드에 대한 생성자를 생성
public class CustomSecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        log.info("---------security config------------");

        return http
                .csrf(httpSecurityCsrfConfigurer -> httpSecurityCsrfConfigurer.disable()) // csrf 설정을 disable로 설정해야 postman에서 테스트 가능
               // .cors(httpSecurityCorsConfigurer -> httpSecurityCorsConfigurer.disable()) // 다른 서버로 요청을 보낼 때 cors 설정을 disable로 설정해야 postman에서 테스트 가능
                .authorizeHttpRequests(authorizeHttpRequestsConfigurer -> authorizeHttpRequestsConfigurer
                        .dispatcherTypeMatchers(DispatcherType.FORWARD).permitAll()
                        .requestMatchers("/user/login", "/user/**","/", "/comments/**", "/creplies/**", "/all", "/home", "/cboard/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/board/**").permitAll()
                        .requestMatchers("/admin/**").hasAuthority("ADMIN")
                        .anyRequest().authenticated())

                .formLogin(formLoginConfigurer -> formLoginConfigurer
                        .loginPage("/user/login") // 로그인 페이지 설정
                        .loginProcessingUrl("/loginProcess") // 로그인 처리 페이지 설정
                        .usernameParameter("username") // 로그인 페이지의 username 파라미터 설정
                        .passwordParameter("password") // 로그인 페이지의 password 파라미터 설정
                        .defaultSuccessUrl("/") // 로그인 성공 후 이동할 페이지
                        .permitAll())
                .logout(logoutConfigurer -> logoutConfigurer
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/")
                        .invalidateHttpSession(true)
                        .clearAuthentication(true))
                .build();
    }

    @Bean // password 암호화를 위한 Bean 등록
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public WebSecurityCustomizer configure() {
        return (web) -> web.ignoring() // static resource에 대한 security 설정을 무시하도록 설정
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations());
        //.requestMatchers("/static/**"); // 구버전: static resource에 대한 security 설정을 무시하도록 설정
    }

    @Bean // authenticationManager를 Bean 역할:
    public AuthenticationManager authenticationManagerBean(HttpSecurity http,  // authenticationManager를 Bean을 등록하여
                                                           BCryptPasswordEncoder bCryptPasswordEncoder,
                                                           UserDetailsService userDetailsService)
            throws Exception {
        AuthenticationManagerBuilder builder =
                http.getSharedObject(AuthenticationManagerBuilder.class);
       builder.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
        return builder.build();
    }


}
