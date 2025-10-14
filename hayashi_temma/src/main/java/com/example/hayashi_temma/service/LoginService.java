package com.example.hayashi_temma.service;

import com.example.hayashi_temma.controller.form.LoginForm;
import com.example.hayashi_temma.repository.UsersRepository;
import com.example.hayashi_temma.repository.entity.Users;
import com.example.hayashi_temma.utils.CipherUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginService {
    @Autowired
    UsersRepository usersRepository;

    public String checkLogin(LoginForm form) {
        String encryptedPassword = CipherUtil.encrypt(form.getPassword());
        Users user = usersRepository.findByAccountAndPassword(form.getAccount(), encryptedPassword);

        if (user == null) {
            return "ログインに失敗しました。";
        }

        if (user.getIsStopped() == 1) {
            return "ログインに失敗しました。";
        }

        return null;
    }

    public Users findLoginUser(LoginForm form) {
        String encryptedPassword = CipherUtil.encrypt(form.getPassword());
        return usersRepository.findByAccountAndPassword(form.getAccount(), encryptedPassword);
    }

}
