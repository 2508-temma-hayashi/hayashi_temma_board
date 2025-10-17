package com.example.hayashi_temma.service;

import com.example.hayashi_temma.repository.CommentRepository;
import com.example.hayashi_temma.repository.entity.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeleteCommentService {
    @Autowired
    CommentRepository commentRepository;

    public void deleteComment(int commentId){

        commentRepository.deleteById(commentId);
    }


}
