package com.example.hayashi_temma.controller;

import com.example.hayashi_temma.repository.entity.User;
import com.example.hayashi_temma.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class AdministratorController {
    @Autowired
    UserService userService;

    @GetMapping("/user/list")
    public ModelAndView showUser(){
        //入力フォームがないのでentityをそのまま渡してもいい。
        List<User> userList = userService.getAllUsers();
        ModelAndView mav = new ModelAndView("userList");
        mav.addObject("userList", userList);
        return mav;
    }

    @PostMapping("/user/update/{id}")
    public String updatestatus(@PathVariable("id")int userId){
        userService.updatedStatus(userId);
        return "redirect:/user/list";
    }

}
