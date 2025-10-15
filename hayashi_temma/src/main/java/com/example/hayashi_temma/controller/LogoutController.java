package com.example.hayashi_temma.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LogoutController {

    @Autowired
    HttpSession session;

    @GetMapping("/logout")
    public String logout(){
        session.invalidate();
        return "redirect:/login";
    }
}
