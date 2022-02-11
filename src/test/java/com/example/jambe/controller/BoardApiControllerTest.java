package com.example.jambe.controller;

import com.example.jambe.domain.Board.Board;
import com.example.jambe.domain.Board.BoardRepository;
import com.example.jambe.domain.Member.Member;
import com.example.jambe.domain.Post.Post;
import com.example.jambe.domain.Role;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.*;
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

    @Autowired
    private BoardRepository boardRepository;

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

    @Test
    @Transactional
    public void 페이징_테스트() throws Exception {

        List<Post> postList = new ArrayList<>();
        Board board = Board.builder()
                .id(1L)
                .category("LOL").build();
        Member member = Member.builder()
                        .id(1L)
                        .account("cksgnlcjswo")
                        .name("kim")
                        .nickname("메롱")
                .email("cksgnlcjswooN@naver.com")
                .passwd("1234")
                .role(Role.GUEST).build();

        boardRepository.save(board);

        //dummy data 생성
        for(long i=1;i<=85;++i) {
            Post post = Post.builder()
                    .id(i)
                    .title("tmp"+i)
                    .content("test").build();
            post.setBoard(board);
            post.setMember(member);

            postList.add(post);
        }

        Pageable pageable = PageRequest.of(1,5);

        Page<Post> posts = new PageImpl<>(postList,pageable,10);

        when(postService.findAllByBoard(1L,pageable)).thenReturn(posts);
        when(boardService.findById(anyLong())).thenReturn(board);

        mockMvc.perform(get("/api/v1/board/1?page=1&size=5"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("tmp6")));
    }
}
