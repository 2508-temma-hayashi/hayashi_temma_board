package com.example.hayashi_temma.repository;

import com.example.hayashi_temma.repository.entity.Messages;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessagesRepository extends JpaRepository<Messages, Integer> {

    @Query("""
        SELECT m FROM Messages m
        JOIN FETCH m.user
        ORDER BY m.createdDate DESC
    """)
    List<Messages> findAllMessages();
}
