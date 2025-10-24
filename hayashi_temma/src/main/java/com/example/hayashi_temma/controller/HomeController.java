package com.example.hayashi_temma.controller;

import com.example.hayashi_temma.controller.form.CommentForm;
import com.example.hayashi_temma.controller.form.MessageSearchForm;
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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDateTime;
import java.util.List;

@Controller
public class HomeController {
    @Autowired
    HomeService homeService;
    @Autowired
    CommentService commentService;

    @GetMapping("/home")
    public ModelAndView showHome(@ModelAttribute MessageSearchForm form, HttpSession session){
        ModelAndView mav = new ModelAndView("home");

        // フィルタで入れたメッセージを拾う
        String errorMessages = (String) session.getAttribute("errorMessages");
        if (errorMessages != null) {
            mav.addObject("errorMessages", errorMessages);
            session.removeAttribute("errorMessages"); // 一度表示したら削除
        }

        //ログインしているのが総務人事部かセッションから確認
        User loginUser = (User) session.getAttribute("loginUser");
        //ログインしてなかったらlogin画面に
        if (loginUser == null) {
            return new ModelAndView("redirect:/login");
        }
        String buttonFlag = "OFF";
        if(loginUser.getDepartmentId() == 1){
            buttonFlag = "ON";
        }

        LocalDateTime startDate = (form.getStartDate() != null)
                ? form.getStartDate().atStartOfDay()
                : LocalDateTime.of(2022, 1, 1, 0, 0, 0);

        LocalDateTime endDate = (form.getEndDate() != null)
                ? form.getEndDate().atTime(23, 59, 59)
                : LocalDateTime.now();

        String category = (form.getCategory() != null && !form.getCategory().isBlank())
                ? form.getCategory()
                : null;



        // 投稿一覧取得
        List<Message> messageList = homeService.findFilteredMessages(startDate, endDate, category);
        List<Comment> commentList = commentService.findAllComments();

        //home.html遷移して、ユーザー情報とボタンフラグを渡す。
        mav.addObject("commentForm", new CommentForm());
        mav.addObject("loginUser", loginUser);
        mav.addObject("commentList", commentList);
        mav.addObject("messageList", messageList);
        mav.addObject("buttonFlag", buttonFlag);
        mav.addObject("messageSearchForm", form);
        return mav;
    }
}
