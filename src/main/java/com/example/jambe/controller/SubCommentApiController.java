package com.example.jambe.controller;

import com.example.jambe.domain.SubComment.SubComment;
import com.example.jambe.dto.SubComment.SubCommentRequestDto;
import com.example.jambe.dto.SubComment.SubCommentResponseDto;
import com.example.jambe.service.SubCommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.security.Principal;

@RequiredArgsConstructor
@Controller
public class SubCommentApiController {

    private final SubCommentService subCommentService;

    @PostMapping("/api/v1/board/post/sub-comment")
    public ResponseEntity<SubCommentResponseDto> addsubComment(@RequestBody SubCommentRequestDto subCommentRequestDto,
                                                               Principal principal) {
        String username = principal.getName();

        Long subCommentId = subCommentService.save(subCommentRequestDto,username);
        SubComment subComment = subCommentService.findById(subCommentId);

        SubCommentResponseDto subCommentResponseDto = SubCommentResponseDto
                                                        .builder()
                                                        .content(subComment.getContent())
                                                        .member(username).build();

        return new ResponseEntity(subCommentResponseDto, HttpStatus.CREATED);
    }
}
