package com.example.jambe.handler;

import com.example.jambe.dto.CustomIntegrationDto;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

public class Oauth2SuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) {
        System.out.println("oauth2 login success");

        CustomIntegrationDto customIntegrationDto = (CustomIntegrationDto)authentication.getPrincipal();
        System.out.println("username:"+ customIntegrationDto.getUsername());

        String jwtToken = Jwts.builder()
                .setSubject(customIntegrationDto.getUsername()) // 사용자
                .setIssuedAt(new Date()) // 현재 시간 기반으로 생성
                .setExpiration(new Date(System.currentTimeMillis()+(60000*30))) // 만료 시간 세팅
                .signWith(SignatureAlgorithm.HS512, "secret") // 사용할 암호화 알고리즘, signature에 들어갈 secret 값 세팅
                .compact();

        response.addHeader("Authorization",jwtToken);
    }
}
