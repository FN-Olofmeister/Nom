package com.example.demo.controller;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.EmailService;
import com.example.demo.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;
    private final EmailService emailService;
    private final UserRepository userRepository;
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    public UserController(UserService userService, EmailService emailService, UserRepository userRepository) {
        this.userService = userService;
        this.emailService = emailService;
        this.userRepository = userRepository;
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody User user) {
        try {
            if (!user.isEmailVerified()) {
                return ResponseEntity.badRequest().body("이메일 인증을 완료해야 회원가입이 가능합니다.");
            }
            userService.registerUser(user);
            return ResponseEntity.ok("회원가입이 완료되었습니다!");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/send-verification-code")
    public ResponseEntity<String> sendVerificationCode(@RequestParam String email) {
        logger.info("이메일 인증 코드 전송 요청 받음: {}", email);

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("해당 이메일을 가진 사용자가 존재하지 않습니다."));

        user.generateVerificationCode(); // 인증 코드 전송
        userRepository.save(user); // DB 저장
        emailService.sendVerificationEmail(user.getEmail(), user.getEmailVerificationCode());

        logger.info("이메일 인증 코드 전송 완료: {}", email);
        return ResponseEntity.ok("이메일 인증 코드가 전송되었습니다.");
    }

    @PostMapping("/verify-code")
    public ResponseEntity<String> verifyEmail(@RequestParam String email, @RequestParam String code) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (user.getEmailVerificationCode() != null && user.getEmailVerificationCode().equals(code)) {
                user.setEmailVerified(true);
                user.setEmailVerificationCode(null);
                userRepository.save(user);
                return ResponseEntity.ok("이메일 인증이 완료되었습니다.");
            }
            return ResponseEntity.badRequest().body("인증 코드가 일치하지 않습니다.");
        }
        return ResponseEntity.badRequest().body("해당 이메일을 가진 사용자가 존재하지 않습니다.");
    }
}
