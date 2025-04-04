package org.example.schedule.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class LoginFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String requestURI = httpRequest.getRequestURI();

        // 회원가입, 로그인 요청은 인증 예외 처리
        if (requestURI.equals("/users") || requestURI.equals("/users/login")) {
            chain.doFilter(request, response);
            return;
        }

        // 세션에서 사용자 정보 확인
        HttpSession session = httpRequest.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            httpResponse.sendError(HttpStatus.UNAUTHORIZED.value(), "인증이 필요합니다.");
            return;
        }

        chain.doFilter(request, response);
    }
}