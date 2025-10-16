package com.example.hayashi_temma.controller;

import com.example.hayashi_temma.controller.form.CommentForm;
import com.example.hayashi_temma.controller.form.PostForm;
import com.example.hayashi_temma.repository.entity.User;
import com.example.hayashi_temma.service.CommentService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

import static com.example.hayashi_temma.controller.LoginController.getErrorMessages;

@Controller
public class CommentController {
    @Autowired
    CommentService commentService;

    public ModelAndView saveComent(@ModelAttribute @Validated CommentForm form,
                                   BindingResult result,
                                   HttpSession session){
        //resultからエラーメッセージを取得してリストの変数に入れる
        List<String> errorMessages = getErrorMessages(result);
        //セッションからログインしてる人のIDを取得する
        User user = (User)session.getAttribute("loginUser");
        int userId = user.getId();

        if(result.hasErrors()){
            ModelAndView mav = new ModelAndView("home");
            mav.addObject("errorMessages", errorMessages);
            mav.addObject("commentForm", form);
            return mav;
        }

        //サービスのメソッドを利用する
        commentService.saveComment(form, userId);





    }
}
