package com.example.hayashi_temma.service;

import com.example.hayashi_temma.repository.CommentRepository;

import com.example.hayashi_temma.repository.entity.Comment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {

    @Autowired
    CommentRepository commentRepository;

    public List<Comment> findAllComments() {
        return commentRepository.findAllComments();
    }
}
