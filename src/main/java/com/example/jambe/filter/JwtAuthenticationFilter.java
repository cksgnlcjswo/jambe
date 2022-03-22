package com.example.jambe.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.jambe.domain.Member.Member;
import com.example.jambe.domain.Member.MemberRepository;
import com.example.jambe.dto.CustomIntegrationDto;
import com.example.jambe.dto.Member.MemberDto;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            MemberDto memberdto = objectMapper.readValue(request.getInputStream(), MemberDto.class);
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(memberdto.getAccount(),memberdto.getPasswd());

            //memberService에서 customIntegrationDto 반환
            Authentication authentication = super.getAuthenticationManager().authenticate(authenticationToken);
            CustomIntegrationDto customIntegrationDto = (CustomIntegrationDto) authentication.getPrincipal();

            return authentication;

        } catch(IOException e) {
            e.printStackTrace();;
        }

        return null;
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {

        CustomIntegrationDto customIntegrationDto = (CustomIntegrationDto) authResult.getPrincipal();

        String jwtToken = JWT.create()
                .withSubject("cos")
                .withExpiresAt(new Date(System.currentTimeMillis()+(60000*30)))
                .withClaim("account",customIntegrationDto.getUsername())
                .withClaim("passwd",customIntegrationDto.getPassword())
                .sign(Algorithm.HMAC512("sec"));

        response.addHeader("Authorization","Bearer "+jwtToken);
    }
}
