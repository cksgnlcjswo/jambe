package com.example.jambe.service;

import com.example.jambe.domain.Board.Board;
import com.example.jambe.domain.Board.BoardRepository;
import com.example.jambe.domain.Member.Member;
import com.example.jambe.domain.Member.MemberRepository;
import com.example.jambe.domain.Post.Post;
import com.example.jambe.domain.Post.PostRepository;
import com.example.jambe.dto.Post.PostRequestDto;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PostService {

    private final PostRepository postRepository;
    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;

    public Post save(PostRequestDto postDto) {
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

        return postRepository.save(post);
    }

    public Post findById(Long boardId,Long postId) {
        //boardId와 postId로 원하는 post 리턴하도록.
        return postRepository.findById(postId).get();
    }

    public Long update(Long boardId, Long postId,PostRequestDto postDto) {
        //board와 postId를 모두 가져올수 있도록 하기
        Post post = postRepository.findById(boardId).get();

        post.update(postDto.getContent(),postDto.getTitle());
        return post.getId();
    }

    public Page<Post> findAllByBoard(Long id, Pageable pageable) {
        return postRepository.findAllByBoard(id,pageable);
    }
}
