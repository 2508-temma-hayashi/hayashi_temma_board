package com.example.hayashi_temma.controller;

import com.example.hayashi_temma.controller.form.PostForm;
import com.example.hayashi_temma.repository.entity.User;
import com.example.hayashi_temma.service.PostService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

import static com.example.hayashi_temma.controller.LoginController.getErrorMessages;

@Controller
public class PostController {

    @Autowired
    PostService postService;

    @Autowired
    HttpSession session;

    @GetMapping("/post")
    public ModelAndView showPost(){
        ModelAndView mav = new ModelAndView("post");
        mav.addObject("postForm", new PostForm());
        return mav;
    }

    @PostMapping("/post")
    //もしもmavで渡すものではなかったら普通に「return post;」ってしておけばよい
    //その場合HTMLは"postForm"で受け取ることになる
    public ModelAndView doPost(@ModelAttribute @Validated PostForm form, BindingResult result){
        User loginUser = (User) session.getAttribute("loginUser");
        List<String> errorMessages = getErrorMessages(result);
        if(result.hasErrors()){
            ModelAndView mav = new ModelAndView("post");
            mav.addObject("errorMessages", errorMessages);
            mav.addObject("postForm", form);
            return mav;
        }

        postService.postMessage(form, loginUser);

        return new ModelAndView("redirect:/home");
    }
}
