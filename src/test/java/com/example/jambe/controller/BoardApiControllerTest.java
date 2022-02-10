package com.example.jambe.controller;

import com.example.jambe.domain.Board.Board;
import com.example.jambe.dto.BoardDto;
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
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BoardApiControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean private BoardService boardService;
    @MockBean private PostService postService;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Test
    @WithMockUser(roles="ADMIN")
    public void 게시판생성_테스트() throws Exception {
        BoardDto boardDto = new BoardDto(null,"LOL");

        when(boardService.save(any()))
                .thenReturn(Board.builder()
                .id(1L)
                .category("LOL").build());

        mockMvc.perform(post("/api/v1/board")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(boardDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.category").value("LOL"));
    }

    @Test
    public void 모든게시판종류가져오기_테스트() throws Exception {
        Board board1 = Board.builder()
                .id(1L)
                .category("LOL").build();

        Board board2 = Board.builder()
                .id(2L)
                .category("sports").build();

        Board board3 = Board.builder()
                .id(3L)
                .category("school").build();

        List<Board> boardList = new ArrayList<>();
        boardList.add(board1);
        boardList.add(board2);
        boardList.add(board3);

        when(boardService.findAll()).thenReturn(boardList);

        mockMvc.perform(get("/api/v1/board"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().string(containsString("sports")));
    }
}
