package com.example.jambe.config;

import com.example.jambe.domain.Member.MemberRepository;
import com.example.jambe.filter.JwtAuthenticationFilter;
import com.example.jambe.filter.JwtAuthorizationFilter;
import com.example.jambe.handler.CustomAccessDeniedHandler;
import com.example.jambe.handler.CustomAuthenticationHandler;
import com.example.jambe.handler.Oauth2SuccessHandler;
import com.example.jambe.service.CustomOauth2UserService;
import com.example.jambe.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.filter.CorsFilter;
/*
    Spring security 설정 class.
    Author: 찬휘
*/
@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CorsFilter corsFilter;
    private final CustomOauth2UserService customOauth2UserService;
    private final MemberRepository memberRepository;
    private final MemberService memberService;

    /*
        해당 패턴에 매칭되는 url은 spring security filter chain을 거치지 않음.
    */
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/static/**", "/css/**", "/js/**", "/images/**","/fonts/**",
                "/h2-console/**","/resources/templates/**","/docs/**");
    }
    /*
        spring security jwt 인증 인가 처리위한 flter 추가와 oauth2 로그인 성공 이후 token 제공 filter 추가
    */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.headers().frameOptions().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilter(corsFilter)
                .formLogin().disable()
                .httpBasic().disable()
                .exceptionHandling()
                .accessDeniedHandler(new CustomAccessDeniedHandler())
                .authenticationEntryPoint(new CustomAuthenticationHandler())
                .and()
                .addFilter(new JwtAuthenticationFilter(authenticationManager()))
                .addFilter(new JwtAuthorizationFilter(authenticationManager(),memberRepository))
                .authorizeRequests()
                .antMatchers( "/user/signup","/auth/login","/","/static/**",
                        "/css/**", "/js/**", "/images/**","/fonts/**","/h2-console/**","/profile","/docs/**").permitAll()
                .antMatchers("/api/**").hasRole("GUEST")
                .antMatchers("/board").hasRole("ADMIN")
                .anyRequest().authenticated()
                .and()
                .logout()
                .logoutSuccessUrl("/")
                .deleteCookies("token")
                .and()
                .oauth2Login()
                .successHandler(new Oauth2SuccessHandler())
                .userInfoEndpoint()
                .userService(customOauth2UserService);
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(memberService).passwordEncoder(passwordEncoder());
    }
}
