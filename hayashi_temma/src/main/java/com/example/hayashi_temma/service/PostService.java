package com.example.hayashi_temma.service;

import com.example.hayashi_temma.controller.form.PostForm;
import com.example.hayashi_temma.repository.MessageRepository;
import com.example.hayashi_temma.repository.entity.Message;
import com.example.hayashi_temma.repository.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class PostService {

    @Autowired
    MessageRepository messageRepository;

    public void postMessage(PostForm form, User loginUser) {
        //PostformをPostEntityに変換
        Message message = new Message();
        message.setTitle(form.getTitle());
        message.setText(form.getText());
        message.setCategory(form.getCategory());
        message.setUser(loginUser);
        message.setCreatedDate(LocalDateTime.now());
        message.setUpdatedDate(LocalDateTime.now());
        //JPAを継承してるからsaveメソッド使える
        messageRepository.save(message);
    }

}
