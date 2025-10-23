package com.example.hayashi_temma.filter;

import com.example.hayashi_temma.repository.entity.User;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;

import java.io.IOException;


public class LoginFilter implements Filter {
    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {

        // ① HTTP通信として扱えるように型変換
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        // ② セッションを取得（false → 新しく作らない）
        //trueの時はセッションがなかったら新しく作る falseの時はセッションがなかったらnullを返す
        HttpSession session = request.getSession(false);

        // ③ ログインユーザー情報をセッションから取り出す
        User loginUser = (User)session.getAttribute("loginUser");

        // ④ 未ログインなら /login にリダイレクト
        if (loginUser == null || session == null) {
            response.sendRedirect("/login");
            return;  // ここで処理終了（Controllerに行かない）
        }

        // ⑤ ログイン済みなら次へ（Controllerに渡す）
        chain.doFilter(req, res);
    }
}

