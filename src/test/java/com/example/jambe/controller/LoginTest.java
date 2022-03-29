package com.example.jambe.controller;

import com.example.jambe.domain.Member.Member;
import com.example.jambe.domain.Member.MemberRepository;
import com.example.jambe.dto.Member.MemberDto;
import com.example.jambe.service.MemberService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.transaction.Transactional;

import java.io.IOException;
import java.util.HashMap;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.oauth2Login;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class LoginTest {

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private MemberService memberService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ObjectMapper objectMapper;

    private MockMvc mvc;

    @BeforeEach
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(this.context)
                .apply(springSecurity())
                .build();
    }

    @Transactional
    @WithAnonymousUser
    @Test
    public void 자체로그인_테스트() throws Exception {
        // given
        MemberDto memberDto = new MemberDto();
        memberDto.setAccount("cksgnlcjswo");
        memberDto.setPasswd("759azs");
        memberDto.setName("cksgnl");
        memberDto.setNickname("메롱");
        memberDto.setEmail("cksgnlcjswoo@naver.com");

        String userId = memberDto.getAccount();
        String password = memberDto.getPasswd();

        HashMap<String,String> loginInfo = new HashMap<String,String>();
        loginInfo.put("account",userId);
        loginInfo.put("passwd",password);

        memberService.joinUser(memberDto);

        // when
        mvc.perform(post("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginInfo)))
                .andExpect(status().is2xxSuccessful())
                .andExpect(authenticated())
                .andExpect(cookie().exists("token"));
    }

    @Transactional
    @WithAnonymousUser
    @Test
    public void oauth2_로그인테스트() throws Exception {
        mvc.perform(get("/auth/login").with(oauth2Login()
                        .authorities(new SimpleGrantedAuthority("ROLE_GUEST"))
                        .attributes(attributes -> {
                            attributes.put("username", "cksgnlcjswo");
                            attributes.put("name", "찬휘킴");
                            attributes.put("email", "cksgnlcjswoo@naver.com");
                        })))
                .andExpect(status().is2xxSuccessful())
                .andExpect(authenticated());
    }

    @WithMockUser
    @Test
    public void 로그아웃_테스트() throws Exception {
        mvc.perform(post("/logout"))
                .andExpect(status().is3xxRedirection()) // redirect 발생
                .andExpect(redirectedUrl("/"))
                .andExpect(unauthenticated());
    }
}
