package com.example.jambe.filter;

import com.example.jambe.domain.Member.Member;
import com.example.jambe.domain.Member.MemberRepository;
import com.example.jambe.dto.CustomIntegrationDto;
import io.jsonwebtoken.Jwts;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    private MemberRepository memberRepository;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager,
                                  MemberRepository memberRepository) {
        super(authenticationManager);
        this.memberRepository = memberRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {

        String jwtHeader = request.getHeader("Authorization");

        if(jwtHeader == null || !jwtHeader.startsWith("Bearer")) {
            chain.doFilter(request,response);
            return;
        }

        String token = request.getHeader("Authorization").replace("Bearer ","");
        System.out.println("token: " + token);

        String account = Jwts.parser()
                .setSigningKey("secret")
                .parseClaimsJws(token)
                .getBody().getSubject();
        //서명 완료
        if(account != null) {
            System.out.println("account is not null");
            Member member = memberRepository.findByAccount(account).get();

            List<GrantedAuthority> authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority(member.getRoleKey()));

            CustomIntegrationDto customIntegrationDto = new CustomIntegrationDto(member,
                    member.getAccount(),
                    member.getPasswd(),
                    authorities);

            Authentication authentication =
                   new UsernamePasswordAuthenticationToken(customIntegrationDto,null, customIntegrationDto.getAuthorities());

            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        System.out.println(SecurityContextHolder.getContext().getAuthentication());
        chain.doFilter(request,response);
    }
}
