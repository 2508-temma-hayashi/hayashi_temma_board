package com.example.hayashi_temma.service;

import com.example.hayashi_temma.repository.MessageRepository;
import com.example.hayashi_temma.repository.entity.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class HomeService {
    @Autowired
    MessageRepository messageRepository;

    public List<Message> findFilteredMessages(LocalDateTime start, LocalDateTime end, String category) {
        return messageRepository.findFilteredMessages(start, end, category);
    }
}
