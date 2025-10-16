package com.example.hayashi_temma.service;

import com.example.hayashi_temma.controller.form.CommentForm;
import com.example.hayashi_temma.repository.CommentRepository;

import com.example.hayashi_temma.repository.entity.Comment;

import com.example.hayashi_temma.repository.entity.Message;
import com.example.hayashi_temma.repository.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CommentService {

    @Autowired
    CommentRepository commentRepository;

    public List<Comment> findAllComments() {
        return commentRepository.findAllComments();
    }

    public void saveComment(CommentForm form, int userId){
        Comment comment = new Comment();
        User user = new User();
        Message message = new Message();

        //コメントのTEXTをFORMからENTITYに変換
        comment.setText(form.getText());
        //CONTROLLERで取得してきたUSERIDを新しく作ったuserオブジェクトに入れる
        user.setId(userId);
        comment.setUser(user);//そのuserオブジェクトをENTITYの中に入れる
        //コメント先のメッセージIDをFORMから取得してそれを新しく作ったmessageオブジェクトに入れる
        message.setId(form.getMessageId());
        comment.setMessage(message);//そのmessageオブジェクトをENTITYの中に入れる
        comment.setCreatedDate(LocalDateTime.now());
        comment.setUpdatedDate(LocalDateTime.now());
        commentRepository.save(comment);

    }

}
