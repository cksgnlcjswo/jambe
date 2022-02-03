package com.example.jambe.controller;

import com.example.jambe.domain.Member.Member;
import com.example.jambe.domain.Member.MemberRepository;
import com.example.jambe.domain.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.transaction.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class LoginTest {

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    private MockMvc mvc;

    @BeforeEach
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(this.context)
                .apply(springSecurity())
                .build();

        memberRepository.save(Member.builder()
                .id(1L)
                .account("cksgnlcjswo")
                .name("찬휘킴")
                .nickname("메롱")
                .email("cksgnlcjswoo@naver.com")
                .passwd(passwordEncoder.encode("759azs"))
                .role(Role.GUEST).build());
    }

    @Transactional
    @WithAnonymousUser
    @Test
    public void 자체로그인_테스트() throws Exception {
        // given
        String userId = "cksgnlcjswo";
        String password = "759azs";

        // when
        mvc.perform(formLogin("/user/login").user(userId).password(password))
                // then
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));
    }
}
