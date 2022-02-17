package com.example.jambe.controller;

import com.example.jambe.domain.Comment.Comment;
import com.example.jambe.dto.Comment.CommentRequestDto;
import com.example.jambe.dto.Comment.CommentResponseDto;
import com.example.jambe.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RequiredArgsConstructor
@Controller
public class CommentApiController {

    private static Logger logger = LoggerFactory.getLogger(CommentApiController.class);
    private final CommentService commentService;

    @ResponseBody
    @PostMapping("/api/v1/board/post/comment")
    public ResponseEntity<CommentResponseDto> saveComment(@RequestBody CommentRequestDto commentRequestDto,
                                                          Principal principal) {
        String username = principal.getName();
        Long commentId = commentService.save(commentRequestDto,username);
        Comment comment = commentService.findById(commentId);

        CommentResponseDto commentResponseDto = CommentResponseDto.builder()
                                                .content(comment.getContent())
                                                .member(comment.getMember().getName())
                                                .build();

        return new ResponseEntity(commentResponseDto, HttpStatus.CREATED);
    }
}
