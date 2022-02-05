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

    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    @PostMapping("/api/v1/board")
    public String save(@RequestBody BoardDto boardDto) {

        boardService.save(boardDto);
        return boardDto.getCategory() + "is created";
    }

    @GetMapping("api/v1/board")
    public void allBoard(Model model) {

        List<Board> boardList = boardService.findAll();
        model.addAttribute("Board",boardList);
    }
}
