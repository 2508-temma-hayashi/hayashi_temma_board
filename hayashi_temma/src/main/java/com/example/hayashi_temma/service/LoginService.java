package com.example.hayashi_temma.service;

import com.example.hayashi_temma.controller.form.LoginForm;
import com.example.hayashi_temma.repository.UserRepository;
import com.example.hayashi_temma.repository.entity.User;
import com.example.hayashi_temma.utils.CipherUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginService {
    @Autowired
    UserRepository userRepository;

    //ユーザー情報が存在するか、停止中のアカウントではないかのチェック
    public String checkLogin(LoginForm form) {
        String encryptedPassword = CipherUtil.encrypt(form.getPassword());
        User user = userRepository.findByAccountAndPassword(form.getAccount(), encryptedPassword);

        if (user == null) {
            return "ログインに失敗しました。";
        }

        if (user.getIsStopped() == 1) {
            return "ログインに失敗しました。";
        }

        return null;
    }

    public User findLoginUser(LoginForm form) {
        String encryptedPassword = CipherUtil.encrypt(form.getPassword());
        return userRepository.findByAccountAndPassword(form.getAccount(), encryptedPassword);
    }

}
