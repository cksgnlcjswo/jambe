package com.example.jambe.controller;

import com.example.jambe.domain.Post.Post;
import com.example.jambe.dto.Post.PostRequestDto;
import com.example.jambe.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class PostApiController {

    private final PostService postService;

    //특정 board에서 post저장
    @PostMapping("/api/v1/board/{id}/post")
    public Long save(@RequestBody PostRequestDto postDto,
                     @PathVariable Long id) {
        return postService.save(postDto);
    }

    //특정 board에서 특정 post 조회
    @GetMapping("/api/v1/board/{boardId}/post/{postId}")
    public void findByPostId(@PathVariable Long boardId,
                             @PathVariable Long postId,
                             Model model) {

        Post post = postService.findById(boardId,postId);

        model.addAttribute("post",post);
        /*
        * 댓글 처리
        * */

        //return template 반환
    }

    @PutMapping("/api/v1/board/{boardId}/post/{postId}")
    public Long update(@PathVariable Long boardId,
                       @PathVariable Long postId,
                       @RequestBody PostRequestDto postDto) {
        return postService.update(boardId,postId,postDto);
    }
}
