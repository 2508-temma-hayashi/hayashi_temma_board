package com.example.hayashi_temma.filter;

import com.example.hayashi_temma.repository.entity.User;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;

import java.io.IOException;


@Component
public class AdministratorFilter implements Filter {

    @Override
    //「Filter を使うときは、doFilter(ServletRequest, ServletResponse, FilterChain) って名前・引数で書いてね」ってルールがある。
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {

        //Spring Boot や Tomcat が動かしてるのは HTTP通信。
        //getSession() や sendRedirect() は「HttpServletRequest」や「HttpServletResponse」専用のメソッド
        //それらを使うためにこれをキャストしておく
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        //FilterはSpringよりも先に呼ばれているので自動注入してくれない。書くしかない。
        HttpSession session = request.getSession();
        User loginUser = (User)session.getAttribute("loginUser");

        //総務人事部なのか確認
        if(loginUser.getDepartmentId() != 1){
            session.setAttribute("errorMessage", "無効なアクセスです");
            response.sendRedirect("/home");
            return;
        }

        //総務人事部であれば次の処理へ。
        //chain.doFilterは次の処理へって意味。
        chain.doFilter(request, response);

    }

}
