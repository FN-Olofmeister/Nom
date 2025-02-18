package com.example.demo.repository;

import com.example.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUserId(String userId);  // userId로 조회
    Optional<User> findByEmail(String email);  // 이메일로 조회 // finByEmail -> 이메일이 이미 존재하는지 확인하기 위한 메소드
}
