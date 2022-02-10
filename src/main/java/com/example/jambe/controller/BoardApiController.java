package com.example.jambe.controller;

import com.example.jambe.domain.Board.Board;
import com.example.jambe.domain.Post.Post;
import com.example.jambe.dto.BoardDto;
import com.example.jambe.service.BoardService;
import com.example.jambe.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class BoardApiController {

    private final BoardService boardService;
    private final PostService postService;

    //게시판 생성

    @ResponseBody
    @PostMapping("/api/v1/board")
    public ResponseEntity<Board> save(@RequestBody BoardDto boardDto) {

        return new ResponseEntity(boardService.save(boardDto), HttpStatus.CREATED);
    }

    //admin용 게시판 생성 화면
    @GetMapping("/board")
    public String make() {

        return "makeBoard";
    }

    //모든 종류의 게시판 보여주기
    @GetMapping("/api/v1/board")
    public String allBoard(Model model) {

        List<Board> boardList = boardService.findAll();
        model.addAttribute("Board",boardList);

        return "boardList";
    }

    //종류별 게시판 입장
    //ex) /api/v1/board1/1
    @GetMapping("/api/v1/board/{id}")
    public String Board(@PathVariable Long id,
                        @PageableDefault(size = 5) Pageable pageable,
                        Model model) {

        Board board = boardService.findById(id);
        Page<Post> posts = postService.findAllByBoard(id,pageable);

        model.addAttribute("pageCount",posts.getTotalPages());
        model.addAttribute("Posts",posts);
        model.addAttribute("Board",board);

        return "board";
    }
}
