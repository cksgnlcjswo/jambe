package com.example.jambe.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NoArgsConstructor;
import net.minidev.json.JSONObject;
import org.apache.catalina.filters.ExpiresFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/*
* 권한 없는 사용자 접근시 호출
* */
@Component
public class CustomAuthenticationHandler implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest httpServletRequest,
                         HttpServletResponse httpServletResponse,
                         AuthenticationException e) throws IOException, ServletException {
        System.out.println("unauthorized user access");

        httpServletResponse.setContentType("text/html; charset=UTF-8");
        httpServletResponse.setStatus(HttpStatus.UNAUTHORIZED.value());

        /*
        JSONObject responseJson = new JSONObject();
        responseJson.put("message","unauthorized access");
        responseJson.put("code",HttpStatus.UNAUTHORIZED);
        responseJson.put("redirectionUrl",redirectUrl);
        */

        PrintWriter out = httpServletResponse.getWriter();
        String alertText = "move to login page cause No access right at this url";
        out.println("<script>alert('" + alertText + "'); location.href='/auth/login';</script> ");
        out.flush();

        //httpServletResponse.sendRedirect(redirectUrl);
    }
}
