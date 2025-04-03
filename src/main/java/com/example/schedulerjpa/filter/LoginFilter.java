package com.example.schedulerjpa.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;
//import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
//@Componete를 사용하면 자동 등록이 가능하다.
//그러나 요구사항에 @Configuration을 활용하라고 명시되어있으므로 변경함
public class LoginFilter implements Filter {

    //로그인 여부를 확인하지 않는 URL
    private static final String[] WHITE_LIST = {"/", "/user/signup", "/login", "/logout"};
    private static final String SESSION_KEY = "loginUser";
    private static final AntPathMatcher PATH_MATCHER = new AntPathMatcher(); // URL 패턴 매칭

    @Override
    public void doFilter(
            ServletRequest request,
            ServletResponse response,
            FilterChain filterChain
    ) throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String requestURI = httpRequest.getRequestURI();

        HttpServletResponse httpResponse = (HttpServletResponse) response;

        log.info("로그인 필터 로직 실행 : {}", requestURI);

        if (!isWhiteList(requestURI)) {
            HttpSession session = httpRequest.getSession(false);
            if (session == null || session.getAttribute(SESSION_KEY) == null) {
                httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "로그인이 필요합니다.");
            }
        }

        filterChain.doFilter(request, response);
    }

    // 로그인 여부를 확인하는 URL인지 체크
    private boolean isWhiteList(String requestURI) {
        for (String pattern : WHITE_LIST) {
            if (PATH_MATCHER.match(pattern, requestURI)) {
                return true;
            }
        }
        return false;
    }
}