package com.example.jambe.controller;

import com.example.jambe.config.SecurityConfig;
import com.example.jambe.domain.Comment.Comment;
import com.example.jambe.domain.Member.Member;
import com.example.jambe.domain.Post.Post;
import com.example.jambe.dto.Comment.CommentRequestDto;
import com.example.jambe.service.CommentService;
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

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = CommentApiController.class,
    excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SecurityConfig.class)
    })
public class CommentApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CommentService commentService;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setup() {
        Comment comment = Comment.builder()
                .id(1L)
                .content("test 댓글")
                .build();

        Member member = Member.builder()
                .id(1L)
                .name("cksgnlcjswo")
                .email("cksgnlcjswoo@naver.com")
                .account("cksgnlcjswo")
                .nickname("cc")
                .passwd("1234")
                .build();

        Post post = Post.builder()
                .id(1L)
                .content("빈 내용")
                .title("테스트제목")
                .build();

        comment.setMember(member);
        comment.setPost(post);

        when(commentService.findById(anyLong())).thenReturn(comment);
    }

    @WithMockUser(username="cksgnlcjswo",roles = "GUEST")
    @Test
    public void 댓글생성_테스트() throws Exception {

        CommentRequestDto commentRequestDto = new CommentRequestDto();
        commentRequestDto.setContent("test 댓글");
        commentRequestDto.setPost(1L);

        when(commentService.save(any(CommentRequestDto.class),anyString())).thenReturn(1L);

        mockMvc.perform(post("/api/v1/board/post/comment")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(commentRequestDto))
                .with(csrf()))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.content").value("test 댓글"))
                .andExpect(jsonPath("$.member").value("cksgnlcjswo"));

    }
}
