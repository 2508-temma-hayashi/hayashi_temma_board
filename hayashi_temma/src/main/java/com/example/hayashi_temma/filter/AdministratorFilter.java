package com.example.hayashi_temma.filter;

import com.example.hayashi_temma.repository.entity.User;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;

import java.io.IOException;



//Springが読み取って、クラスからDIしてオブジェクトを作成して、Beanの箱に入れる
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


        HttpSession session = request.getSession();
        User loginUser = (User)session.getAttribute("loginUser");

        //総務人事部なのか確認
        if(loginUser.getDepartmentId() != 1){
            session.setAttribute("errorMessages", "無効なアクセスです");
            response.sendRedirect("/home");
            return;
        }

        //総務人事部であれば次の処理へ。
        //chain.doFilterは次の処理へって意味。
        chain.doFilter(request, response);

    }

}
