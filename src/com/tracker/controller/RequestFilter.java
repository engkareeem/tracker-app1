package com.tracker.controller;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

public class RequestFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest hReq = (HttpServletRequest) req;
        HttpServletResponse hResp = (HttpServletResponse) resp;
        HttpSession session = hReq.getSession(false);
        String URI = hReq.getRequestURI();
        if(session == null || session.getAttribute("employee") == null) {
            if(URI.equals("/login")) {
                filterChain.doFilter(req, resp);
            } else {
                hResp.sendRedirect("/login");
            }
        } else {
            if(URI.equals("/login")) {
                hResp.sendRedirect("/");
            } else {
                filterChain.doFilter(req, resp);
            }
        }
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
