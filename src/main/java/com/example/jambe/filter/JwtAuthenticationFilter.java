package com.example.jambe.filter;

import com.example.jambe.dto.CustomIntegrationDto;
import com.example.jambe.dto.Member.MemberDto;
import com.example.jambe.util.ResponseOuterUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
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

    private AuthenticationManager authenticationManager;
    public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
        //setFilterProcessesUrl() 지정한 url로 로그인 url 설정 가능
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            MemberDto memberdto = objectMapper.readValue(request.getInputStream(), MemberDto.class);
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(memberdto.getAccount(),memberdto.getPasswd());

            return authenticationManager.authenticate(authenticationToken);

        } catch(BadCredentialsException badCredentialsException) {
            ResponseOuterUtil responseOuterUtil = new ResponseOuterUtil(request,response);
            responseOuterUtil.send("존재하지 않는 비밀번호 입니다.",404);
        } catch(InternalAuthenticationServiceException internalAuthenticationServiceException) {
            System.out.println("존재하징 않는 사용자");
            ResponseOuterUtil responseOuterUtil = new ResponseOuterUtil(request,response);
            responseOuterUtil.send("존재하지 않는 아이디",401);
        } catch(IOException e) {
            e.printStackTrace();;
        }

        return null;
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        System.out.println("login success");
        CustomIntegrationDto customIntegrationDto = (CustomIntegrationDto) authResult.getPrincipal();

        String jwtToken = Jwts.builder()
                .setSubject(customIntegrationDto.getUsername()) // 사용자
                .setIssuedAt(new Date()) // 현재 시간 기반으로 생성
                .setExpiration(new Date(System.currentTimeMillis()+(60000*30))) // 만료 시간 세팅
                .signWith(SignatureAlgorithm.HS512, "secret") // 사용할 암호화 알고리즘, signature에 들어갈 secret 값 세팅
                .compact();

        response.addHeader("Authorization",jwtToken);
    }
}
