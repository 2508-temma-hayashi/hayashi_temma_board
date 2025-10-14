package com.example.hayashi_temma.service;

import com.example.hayashi_temma.repository.MessagesRepository;
import com.example.hayashi_temma.repository.entity.Messages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HomeService {
    @Autowired
    MessagesRepository messagesRepository;

    public List<Messages> findAllMessages(){
        return messagesRepository.findAllMessages();
    }
}
