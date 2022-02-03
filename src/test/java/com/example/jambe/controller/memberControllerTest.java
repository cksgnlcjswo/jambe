package com.example.jambe.controller;

import com.example.jambe.config.SecurityConfig;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.oauth2Login;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = IndexController.class,
        excludeFilters = {
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SecurityConfig.class)
        })

public class memberControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void oauth2_로그인테스트() throws Exception {
        mockMvc.perform(get("/login").with(oauth2Login()
                        .authorities(new SimpleGrantedAuthority("ROLE_GUEST"))
                        .attributes(attributes -> {
                            attributes.put("username", "cksgnlcjswo");
                            attributes.put("name", "찬휘킴");
                            attributes.put("email", "cksgnlcjswoo@naver.com");
                })))
                .andExpect(status().isOk());
    }
}
