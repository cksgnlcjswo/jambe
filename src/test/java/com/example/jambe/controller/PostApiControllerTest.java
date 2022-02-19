package com.example.jambe.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import com.example.jambe.config.SecurityConfig;
import com.example.jambe.domain.Board.Board;
import com.example.jambe.domain.Member.Member;
import com.example.jambe.domain.Post.Post;
import com.example.jambe.dto.Post.PostRequestDto;
import com.example.jambe.service.CommentService;
import com.example.jambe.service.MemberService;
import com.example.jambe.service.PostService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;


@WebMvcTest(controllers = PostApiController.class,
        excludeFilters = {
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SecurityConfig.class)
        })
public class PostApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean private MemberService memberService;
    @MockBean private PostService postService;
    @MockBean private CommentService commentService;

    @BeforeEach
    void setup() {
        Post post = Post.builder()
                .id(1L)
                .content("test content")
                .title("test title")
                .build();

        Board board = Board.builder()
                        .id(1L)
                        .category("LOL").build();

        Member member = Member.builder()
                        .id(1L)
                        .account("cksgnlcjswo")
                        .nickname("hello")
                        .name("찬휘킴")
                        .email("cksgnlcjswoo@naver.com")
                        .passwd("759azs")
                        .build();

        post.setMember(member);
        post.setBoard(board);

        when(postService.save(any())).thenReturn(1L);
        when(postService.findById(anyLong())).thenReturn(post);
    }

    @Test
    @WithMockUser(roles="GUEST")
    public void 포스트등록_테스트() throws Exception {

        PostRequestDto postRequestDto = new PostRequestDto(1L,1L,1L,"test content","test title");

        mockMvc.perform(post("/api/v1/board/post")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(postRequestDto)).with(csrf()))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(postRequestDto.getId()))
                .andExpect(jsonPath("$.content").value(postRequestDto.getContent()))
                .andExpect(jsonPath("$.title").value(postRequestDto.getTitle()))
                .andExpect(jsonPath("$.member").value("찬휘킴"))
                .andExpect(jsonPath("$.board").value("LOL"));
    }
}
