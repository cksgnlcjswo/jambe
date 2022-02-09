package com.example.jambe.controller;

import com.example.jambe.domain.Post.Post;
import com.example.jambe.dto.Post.PostRequestDto;
import com.example.jambe.dto.Post.PostUpdateRequestDto;
import com.example.jambe.service.MemberService;
import com.example.jambe.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@Controller
public class PostApiController {

    private final PostService postService;
    private final MemberService memberService;

    //특정 board에서 post저장
    @PostMapping("/api/v1/board/post")
    public String save(PostRequestDto postDto) {
        postService.save(postDto);
        return "redirect:/api/v1/board/"+postDto.getBoard();
    }

    //특정 board에서 특정 post 조회
    @GetMapping("/api/v1/board/post/{postId}")
    public String findByPostId(@PathVariable Long postId, Model model) {

        Post post = postService.findById(postId);

        model.addAttribute("post",post);
        /*
        * 댓글 처리
        * */

        return "boardContent";
    }

    //글쓰기 페이지
    @GetMapping("/api/v1/board/{boardId}/post")
    public String post(@PathVariable Long boardId,
                       Model model,
                       Authentication authentication) {

        String username = authentication.getName();
        Long id = memberService.findByName(username);

        model.addAttribute("userId",id);
        model.addAttribute("boardId",boardId);
        return "writePost";
    }

    @ResponseBody
    @PutMapping("/api/v1/board/post/{postId}")
    public Long update(@PathVariable Long postId,
                       @RequestBody PostUpdateRequestDto postDto) {
        return postService.update(postId,postDto);
    }

    @ResponseBody
    @DeleteMapping("/api/v1/board/post/{postId}")
    public Long delete(@PathVariable Long postId) {
        return postService.delete(postId);
    }
}
