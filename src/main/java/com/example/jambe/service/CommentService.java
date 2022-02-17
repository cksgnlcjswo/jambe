package com.example.jambe.service;

import com.example.jambe.controller.CommentApiController;
import com.example.jambe.domain.Board.Board;
import com.example.jambe.domain.Board.BoardRepository;
import com.example.jambe.domain.Comment.Comment;
import com.example.jambe.domain.Comment.CommentRepository;
import com.example.jambe.domain.Member.Member;
import com.example.jambe.domain.Member.MemberRepository;
import com.example.jambe.domain.Post.Post;
import com.example.jambe.domain.Post.PostRepository;
import com.example.jambe.dto.Comment.CommentRequestDto;
import com.example.jambe.dto.Post.PostRequestDto;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CommentService {
    private final static Logger logger = LoggerFactory.getLogger(CommentService.class);
    private final PostRepository postRepository;
    private final MemberRepository memberRepository;
    private final CommentRepository commentRepository;

    public Long save(CommentRequestDto commentDto,String username) {

        Long postId = commentDto.getPost();

        Post post = postRepository.findById(postId).get();
        Member member = memberRepository.findByName(username).get();

        Comment comment = Comment.builder()
                        .id(commentDto.getId())
                        .content(commentDto.getContent())
                        .build();

        comment.setPost(post);
        comment.setMember(member);

        return commentRepository.save(comment).getId();
    }

    public Comment findById(Long id) {
        return commentRepository.findById(id).get();
    }

    public List<Comment> findByPostId(Long postId) {
        return commentRepository.findByPostId(postId);
    }
}
