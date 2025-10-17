package com.example.hayashi_temma.controller;

import com.example.hayashi_temma.repository.entity.User;
import com.example.hayashi_temma.service.DeleteMessageService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class DeleteMessageController {
    @Autowired
    DeleteMessageService deleteMessageService;

    @PostMapping("/message/delete/{id}")
    public ModelAndView deleteMessage(@PathVariable("id") int messageId,
                                      HttpSession session){
        //以前自分がセッションに設定したUserクラスのオブジェクトだから、それを取り出す。
        User loginUser = (User) session.getAttribute("loginUser");
        //サービスのメソッドを使う。
        boolean access = deleteMessageService.deleteMessage(messageId, loginUser.getId());

        //DeleteServiceの結果がfalseということは、投稿のUSERIDとログインしている人のIDが同じではなかったということ。
        if(access == false){
            session.setAttribute("errorMessage", "無効なアクセスです");
            return new ModelAndView("redirect:/home");
        }

        ModelAndView mav = new ModelAndView("redirect:/home");
        return mav;

    }
}
