package com.projectdemo1.domain;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true) //username은 null이면 안되고, 중복되면 안된다.
    private String username; // 계정 아이디
    private String password;
    private String email;
    private String role;
}
