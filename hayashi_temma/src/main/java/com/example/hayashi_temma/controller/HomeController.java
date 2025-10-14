package com.example.hayashi_temma.controller;

import com.example.hayashi_temma.repository.entity.Messages;
import com.example.hayashi_temma.repository.entity.Users;
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

    @GetMapping("/home")
    public ModelAndView showHome(HttpSession session){
        ModelAndView mav = new ModelAndView("home");

        //ログインしているのが総務人事部かセッションから確認
        Users loginUser = (Users) session.getAttribute("loginUser");
        String buttonFlag = "OFF";
        if(loginUser.getDepartmentId() == 1){
            buttonFlag = "ON";
        }

        // 投稿一覧取得
        List<Messages> messageList = homeService.findAllMessages();

        //home.html遷移して、ユーザー情報とボタンフラグを渡す。
        mav.addObject("loginUser", loginUser);
        mav.addObject("buttonFlag", buttonFlag);
        return mav;
    }
}
