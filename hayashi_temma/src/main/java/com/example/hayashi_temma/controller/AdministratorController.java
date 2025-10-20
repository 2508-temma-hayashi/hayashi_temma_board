package com.example.hayashi_temma.controller;

import com.example.hayashi_temma.repository.entity.User;
import com.example.hayashi_temma.service.UserSerivice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class AdministratorController {
    @Autowired
    UserSerivice userSerivice;

    @GetMapping("/user/list")
    public ModelAndView showUser(){
        //入力フォームがないのでentityをそのまま渡してもいい。
        List<User> userList = userSerivice.getAllUsers();
        ModelAndView mav = new ModelAndView("userList");
        mav.addObject("userList", userList);
        return mav;
    }

    @PostMapping("/user/update/{id}")
    public String updatestatus(@PathVariable("id")int userId){
        userSerivice.updatedStatus(userId);
        return "redirect:/user/list";
    }

}
