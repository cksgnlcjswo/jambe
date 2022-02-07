package com.example.jambe.controller;

import com.example.jambe.domain.Board.Board;
import com.example.jambe.dto.BoardDto;
import com.example.jambe.service.BoardService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
public class BoardApiController {

    private final BoardService boardService;

    @ResponseBody
    @PostMapping("/api/v1/board")
    public String save(@RequestBody BoardDto boardDto) {

        boardService.save(boardDto);
        return boardDto.getCategory() + "is created";
    }

    //모든 종류의 게시판 보여주기
    @GetMapping("/api/v1/board")
    public void allBoard(Model model) {

        List<Board> boardList = boardService.findAll();
        model.addAttribute("Board",boardList);
    }

    //종류별 게시판 입장
    //ex) /api/v1/board?id=1
    @GetMapping("/api/v1/board/{id}")
    public void Board(@PathVariable Long id,
                      Model model) {

        Board board = boardService.findById(id);
        model.addAttribute("category",board.getCategory());
        model.addAttribute("posts",board.getPosts());
    }
}
