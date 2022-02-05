package com.example.jambe.service.Board;

import com.example.jambe.domain.Board.Board;
import com.example.jambe.domain.Board.BoardRepository;
import com.example.jambe.dto.BoardDto;
import com.example.jambe.service.BoardService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class BoardServiceTest {

    BoardService boardService;

    @MockBean
    private BoardRepository boardRepository;

    @BeforeEach
    public void setup() {
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

        when(boardRepository.findAll()).thenReturn(boardList);
        boardService = new BoardService(boardRepository);
    }

    @Test
    public void findAll_테스트() {

        List<Board> boardList = boardService.findAll();
        assertThat(boardList.size()).isEqualTo(3);

    }
}
