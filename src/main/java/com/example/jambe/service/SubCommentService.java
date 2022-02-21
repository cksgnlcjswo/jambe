package com.example.jambe.service;

import com.example.jambe.domain.Comment.Comment;
import com.example.jambe.domain.Comment.CommentRepository;
import com.example.jambe.domain.Member.Member;
import com.example.jambe.domain.Member.MemberRepository;
import com.example.jambe.domain.Post.Post;
import com.example.jambe.domain.SubComment.SubComment;
import com.example.jambe.domain.SubComment.SubCommentRepository;
import com.example.jambe.dto.SubComment.SubCommentRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class SubCommentService {

    private final CommentRepository commentRepository;
    private final MemberRepository memberRepository;
    private final SubCommentRepository subCommentRepository;

    public Long save(SubCommentRequestDto subCommentRequestDto, String username) {
        Long commentId = subCommentRequestDto.getComment();

        Comment comment =  commentRepository.findById(commentId).get();
        Member member = memberRepository.findByName(username).get();

        SubComment subComment = SubComment.builder()
                                .id(subCommentRequestDto.getId())
                                .content(subCommentRequestDto.getContent()).build();

        subComment.setComment(comment);
        subComment.setMember(member);

        return subCommentRepository.save(subComment).getId();
    }

    public SubComment findById(Long subCommentId) {

        return subCommentRepository.findById(subCommentId).get();
    }
}
