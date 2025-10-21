package com.example.hayashi_temma.repository;

import com.example.hayashi_temma.repository.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    @Query("""
        SELECT u FROM User u
        JOIN FETCH u.branch
        JOIN FETCH u.department
        ORDER BY u.id ASC
    """)
    List<User> findAllUsersWithBranchAndDepartment();
    User findByAccountAndPassword(String account, String password);
    User findByAccount(String account);
}