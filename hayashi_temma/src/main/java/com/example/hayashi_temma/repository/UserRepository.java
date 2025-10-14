package com.example.hayashi_temma.repository;

import com.example.hayashi_temma.repository.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findByAccountAndPassword(String account, String password);
}