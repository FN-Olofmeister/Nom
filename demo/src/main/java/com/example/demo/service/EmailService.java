package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class EmailService {
    private final JavaMailSender mailSender;
    private static final Logger logger = LoggerFactory.getLogger(EmailService.class);

    @Autowired
    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendVerificationEmail(String to, String code) {
        try {
            logger.info("이메일 전송 시작: {}", to);

            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setTo(to);
            helper.setSubject("이메일 인증 코드");
            helper.setText("이메일 인증 코드: " + code, true);

            mailSender.send(message);

            logger.info("이메일 전송 완료: {}", to);
        } catch (MessagingException e) {
            logger.error("이메일 전송 실패: {}", e.getMessage());
            throw new RuntimeException("이메일 전송 실패: " + e.getMessage());
        }
    }
}
