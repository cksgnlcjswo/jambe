package com.example.jambe.controller;

import com.example.jambe.domain.Comment.Comment;
import com.example.jambe.domain.Post.Post;
import com.example.jambe.dto.Post.PostRequestDto;
import com.example.jambe.dto.Post.PostResponseDto;
import com.example.jambe.dto.Post.PostUpdateRequestDto;
import com.example.jambe.service.CommentService;
import com.example.jambe.service.MemberService;
import com.example.jambe.service.PostService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class PostApiController {

    private final PostService postService;
    private final MemberService memberService;
    private final CommentService commentService;

    //특정 board에서 post저장
    @PostMapping("/api/v1/board/post")
    public ResponseEntity<PostResponseDto> save(@RequestBody PostRequestDto postDto) {
        Long postId = postService.save(postDto);
        Post post = postService.findById(postId);

        return new ResponseEntity(PostResponseDto.builder()
                .id(post.getId())
                .board(post.getBoard().getCategory())
                .member(post.getMember().getName())
                .content(post.getContent())
                .title(post.getTitle()).build(),HttpStatus.CREATED);
    }

    //특정 board에서 특정 post 조회
    @GetMapping("/api/v1/board/post/{postId}")
    public String findByPostId(@PathVariable Long postId, Model model) {

        Post post = postService.findById(postId);

        model.addAttribute("post",post);

        List<Comment> commentList = commentService.findByPostId(postId);
        model.addAttribute("commentList",commentList);

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
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/api/v1/board/post/{postId}")
    public Long delete(@PathVariable Long postId) {
        return postService.delete(postId);
    }
}
