package com.example.hayashi_temma.filter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;

@WebFilter(urlPatterns = {"/user/list"})
@Component
public interface administratorFilter implements Filter {
    @Override
    public void doFilter(HttpServletRequest requet, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        Http

    }

}
