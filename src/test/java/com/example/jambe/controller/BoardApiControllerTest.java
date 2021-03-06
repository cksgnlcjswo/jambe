package com.example.jambe.controller;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import com.example.jambe.config.SecurityConfig;
import com.example.jambe.domain.Board.Board;
import com.example.jambe.domain.Member.Member;
import com.example.jambe.domain.Post.Post;
import com.example.jambe.dto.BoardDto;
import com.example.jambe.service.BoardService;
import com.example.jambe.service.PostService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.collection.IsCollectionWithSize;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

@WebMvcTest(controllers = BoardApiController.class,
        excludeFilters = {
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SecurityConfig.class)
        })
@AutoConfigureRestDocs(outputDir = "build/generated-snippets")
public class BoardApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean private BoardService boardService;
    @MockBean private PostService postService;

    @Test
    @WithMockUser(roles="ADMIN")
    public void ???????????????_?????????() throws Exception {
        BoardDto boardDto = new BoardDto(null,"LOL");

        when(boardService.save(any()))
                .thenReturn(Board.builder()
                .id(1L)
                .category("LOL").build());

        mockMvc.perform(post("/api/v1/board")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(boardDto))
                        .with(csrf()))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.category").value("LOL"))
                .andDo(document("post-new-board"));
    }

    @Test
    @WithMockUser(roles="ADMIN")
    public void ?????????????????????????????????_?????????() throws Exception {
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

        mockMvc.perform(get("/api/v1/boards").with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("boardList"))
                .andExpect(model().attributeExists("Board")) //Board?????? ????????? context variable??? ???????????????
                .andExpect(model().attribute("Board", IsCollectionWithSize.hasSize(3))) // jobs model??? size??? 3?????? ??????
                .andDo(document("find-all-board"));
    }

    @Test
    @WithMockUser(roles="USER")
    public void ?????????_?????????() throws Exception {

        List<Post> postList = new ArrayList<>();
        Board board = Board.builder()
                .id(1L)
                .category("LOL").build();
        Member member = Member.builder()
                        .id(1L)
                        .account("cksgnlcjswo")
                        .name("kim")
                        .nickname("??????")
                .email("cksgnlcjswooN@naver.com")
                .passwd("1234").build();

        //dummy data ??????
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
