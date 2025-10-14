package com.example.hayashi_temma.repository;

import com.example.hayashi_temma.repository.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepository extends JpaRepository<Users, Integer> {
    Users findByAccountAndPassword(String account, String password);
}