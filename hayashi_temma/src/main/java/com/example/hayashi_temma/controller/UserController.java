package com.example.hayashi_temma.controller;

import com.example.hayashi_temma.controller.form.UserForm;
import com.example.hayashi_temma.repository.entity.User;
import com.example.hayashi_temma.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

import static com.example.hayashi_temma.controller.LoginController.getErrorMessages;

@Controller
public class UserController {
    @Autowired
    UserService userService;

    //ユーザ登録画面表示
    @GetMapping("/user/register")
    public ModelAndView showRegister(){
        ModelAndView mav = new ModelAndView("userRegister");
        //入れ物をからでも渡さないとないよおってHTMLが困る。
        mav.addObject("userForm", new UserForm());
        //フォワード
        return mav;
    }

    //ユーザー登録ボタン押された処理
    @PostMapping("/user/register")
    public ModelAndView userRegister(@ModelAttribute @Validated UserForm form, BindingResult result){
        ModelAndView mav = new ModelAndView("userRegister");

        // ① Formの中にあったエラー取得
        List<String> errorMessages = new ArrayList<>();
        if (result.hasErrors()) {
            errorMessages.addAll(getErrorMessages(result));
        }

        // ② サービスのバリデーション由来のエラー取得
        List<String> customErrors = userService.validate(form);
        if (!customErrors.isEmpty()) {
            errorMessages.addAll(customErrors);
        }

        // ③ まとめたリストにエラーがあればまとめて表示
        if (!errorMessages.isEmpty()) {
            mav.addObject("errorMessages", errorMessages);
            mav.addObject("userForm", form); // 入力保持
            return mav;
        }
        //エラーがなければ入力内容をServiceに渡してDBに保存☞そのあとリダイレクトでユーザー管理画面表示
        userService.registerUser(form);
        return new ModelAndView("redirect:/user/list");
    }

    //ユーザー編集画面表示
    @GetMapping("/user/edit/{id}")
    public ModelAndView showUserEdit(@PathVariable("id") int id,
                                     HttpSession session){
        User loginUser = (User)session.getAttribute("loginUser");
        ModelAndView mav = new ModelAndView("userEdit");
        UserForm form = userService.pickUp(id);
        mav.addObject("loginUser", loginUser);
        mav.addObject("userForm", form);
        return mav;
    }

    //ユーザー編集処理
    @PostMapping("/user/editInfo/{id}")
    public ModelAndView userUpdate(@PathVariable("id") int id, @ModelAttribute @Validated UserForm form, BindingResult result){
        ModelAndView mav = new ModelAndView("userEdit");

        // ① Formの中にあったエラー取得
        List<String> errorMessages = new ArrayList<>();
        if (result.hasErrors()) {
            errorMessages.addAll(getErrorMessages(result));
        }

        // ② サービスのバリデーション由来のエラー取得
        List<String> customErrors = userService.editValidate(form);
        if (!customErrors.isEmpty()) {
            errorMessages.addAll(customErrors);
        }

        // ③ まとめたリストにエラーがあればまとめて表示
        if (!errorMessages.isEmpty()) {
            mav.addObject("errorMessages", errorMessages);
            mav.addObject("userForm", form); // 入力保持
            return mav;
        }
        form.setId(id); //一応書いておく。
        //エラーがなければ入力内容をServiceに渡してDBに保存☞そのあとリダイレクトでユーザー管理画面表示
        userService.updateUser(form);
        return new ModelAndView("redirect:/user/list");
    }


}
