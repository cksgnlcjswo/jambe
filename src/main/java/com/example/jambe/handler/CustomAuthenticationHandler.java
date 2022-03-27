package com.example.jambe.handler;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/*
* 인증 안된 사용자가 접근시 호출
* */

public class CustomAuthenticationHandler implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest httpServletRequest,
                         HttpServletResponse httpServletResponse,
                         AuthenticationException e) throws IOException, ServletException {
        System.out.println(e.getMessage());
        httpServletResponse.setContentType("text/html; charset=UTF-8");
        httpServletResponse.setStatus(HttpStatus.UNAUTHORIZED.value());

        PrintWriter out = httpServletResponse.getWriter();
        String alertText = "move to login page cause No access right at this url";
        out.println(alertText);
        out.flush();
    }
}
