package com.example.demo.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  // 자동 증가 PK

    @Column(nullable = false)
    private String username;  // 사용자 이름

    @Column(nullable = false, unique = true)
    private String userId;  // 사용자 ID (유니크)

    @Column(nullable = false)
    private String password;  // 비밀번호

    @Column(nullable = true)
    private String phone;  // 전화번호 (NULL 허용)

    @Column(nullable = false, unique = true)
    private String email;  // 이메일 (유니크)

    @Column(nullable = false)
    private boolean emailVerified = false;  // 이메일 인증 여부 (기본값 false)

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();  // 회원가입 시간 (수정 불가)

    @Column(nullable = true)
    private String emailVerificationCode;  // 이메일 인증 코드

    // 인증 코드 생성 메서드
    public void generateVerificationCode() {
        this.emailVerificationCode = UUID.randomUUID().toString().substring(0, 6); // 6자리 코드 생성
    }

    // 생성자 (비밀번호 암호화 전에 사용)
    public User(String username, String userId, String password, String phone, String email) {
        this.username = username;
        this.userId = userId;
        this.password = password;
        this.phone = phone;
        this.email = email;
        this.createdAt = LocalDateTime.now();
    }
}
