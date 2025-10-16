package com.example.hayashi_temma.controller;


import com.example.hayashi_temma.controller.form.LoginForm;
import com.example.hayashi_temma.repository.entity.User;
import com.example.hayashi_temma.service.LoginService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Controller
public class LoginController {

    @Autowired
    private LoginService loginService;

    @GetMapping("/login")
    public ModelAndView showLogin(){
        ModelAndView mav = new ModelAndView("login");

        List<String> errorMessages = new ArrayList<>();

        return mav;
    }


    @PostMapping("/login")
    //@ModelAttributeがHTMLのth:object="${loginForm}" に対応するものをバインドしてformに入れてそれにloginForm属性を付与する
    public ModelAndView doLogin(@ModelAttribute @Validated LoginForm form, BindingResult result, HttpSession session){

        List<String> errorMessages = getErrorMessages(result);

        //アノテーションでチェックしている入力、文字数チェック
        if (result.hasErrors()) {
            ModelAndView mav = new ModelAndView("login");
            mav.addObject("errorMessages", errorMessages);
            mav.addObject("loginForm", form); // 入力内容を保持するために
            return mav;
        }

        //停止中のアカウントではないかの確認と、アカウントが存在するかの確認
        String errorMessage = loginService.checkLogin(form);
        if (errorMessage != null) {
            ModelAndView mav = new ModelAndView("login");
            errorMessages.add(errorMessage);
            mav.addObject("errorMessages", errorMessages);
            mav.addObject("loginForm", form);//入力内容を保持するために
            return mav;
        }
        //フォームの中身（入力値）だけでは信用できないからDBから持ってきてる
        User user = loginService.findLoginUser(form);
        session.setAttribute("loginUser", user);
        return new ModelAndView("redirect:/home");
    }

    public static List<String> getErrorMessages(BindingResult result) {
        List<String> errorMessages = new ArrayList<>();
        if (result.hasErrors()) {
            for (FieldError error : result.getFieldErrors()) {
                errorMessages.add(error.getDefaultMessage());
            }
        }
        return errorMessages;
    }
}
