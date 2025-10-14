package com.example.hayashi_temma.controller;

import com.example.hayashi_temma.repository.entity.Comment;
import com.example.hayashi_temma.repository.entity.Message;
import com.example.hayashi_temma.repository.entity.User;
import com.example.hayashi_temma.repository.entity.User;
import com.example.hayashi_temma.service.CommentService;
import com.example.hayashi_temma.service.HomeService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class HomeController {
    @Autowired
    HomeService homeService;
    @Autowired
    CommentService commentService;

    @GetMapping("/home")
    public ModelAndView showHome(HttpSession session){
        ModelAndView mav = new ModelAndView("home");

        //ログインしているのが総務人事部かセッションから確認
        User loginUser = (User) session.getAttribute("loginUser");
        //ログインしてなかったrあlogin画面に
        if (loginUser == null) {
            return new ModelAndView("redirect:/login");
        }
        String buttonFlag = "OFF";
        if(loginUser.getDepartmentId() == 1){
            buttonFlag = "ON";
        }

        // 投稿一覧取得
        List<Message> messageList = homeService.findAllMessages();
        List<Comment> commentList = commentService.findAllComments();

        //home.html遷移して、ユーザー情報とボタンフラグを渡す。
        mav.addObject("loginUser", loginUser);
        mav.addObject("messageList", messageList);
        mav.addObject("buttonFlag", buttonFlag);
        return mav;
    }
}
