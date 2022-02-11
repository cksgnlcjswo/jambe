package com.example.jambe.controller;

import com.example.jambe.domain.Board.BoardRepository;
import com.example.jambe.domain.Post.Post;
import com.example.jambe.dto.Post.PostRequestDto;
import com.example.jambe.service.BoardService;
import com.example.jambe.service.PostService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PostApiControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private BoardRepository boardRepository;

    @MockBean
    private BoardService boardService;
    @MockBean private PostService postService;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Test
    public void 포스트등록_테스트() throws Exception{

        PostRequestDto postRequestDto = new PostRequestDto(1L,1L,1L,"테스트 내용","테스트 제목");

        when(postService.save(any())).thenReturn(1L);
        when(postService.findById(anyLong())).thenReturn(Post.builder()
                .id(postRequestDto.getId())
                .content(postRequestDto.getContent())
                .title(postRequestDto.getTitle())
                .build());

        mockMvc.perform(post("/api/v1/board/post")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(postRequestDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(postRequestDto.getId()))
                .andExpect(jsonPath("$.content").value(postRequestDto.getContent()))
                .andExpect(jsonPath("$.title").value(postRequestDto.getTitle()));

    }
}
