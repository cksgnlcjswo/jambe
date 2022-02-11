package com.example.jambe.service;

import com.example.jambe.domain.Board.Board;
import com.example.jambe.domain.Board.BoardRepository;
import com.example.jambe.domain.Member.Member;
import com.example.jambe.domain.Member.MemberRepository;
import com.example.jambe.domain.Post.Post;
import com.example.jambe.domain.Post.PostRepository;
import com.example.jambe.dto.Post.PostRequestDto;
import com.example.jambe.dto.Post.PostUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class PostService {

    private final PostRepository postRepository;
    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;

    public Long save(PostRequestDto postDto) {
        Long boardId = postDto.getBoard();
        Long memberId = postDto.getMember();

        Board board = boardRepository.findById(boardId).get();
        Member member = memberRepository.findById(memberId).get();

        Post post = Post.builder()
                .id(postDto.getId())
                .title(postDto.getTitle())
                .content(postDto.getContent()).build();

        post.setBoard(board);
        post.setMember(member);

        return postRepository.save(post).getId();
    }

    public Post findById(Long postId) {
        return postRepository.findById(postId).get();
    }

    public Long update(Long postId, PostUpdateRequestDto postDto) {

        Post post = postRepository.findById(postId).get();
        post.update(postDto.getContent(),postDto.getTitle());
        return postRepository.save(post).getId();
    }

    public Long delete(Long postId) {
        Post post = postRepository.findById(postId).get();
        postRepository.delete(post);
        return postId;
    }

    public Page<Post> findAllByBoard(Long id, Pageable pageable) {
        return postRepository.findAllByBoard(id,pageable);
    }
}
