package com.example.hayashi_temma.repository;

import com.example.hayashi_temma.repository.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer> {

    @Query("""
        SELECT m FROM Message m
        JOIN FETCH m.user
        ORDER BY m.createdDate DESC
    """)
    List<Message> findAllMessages();
}
