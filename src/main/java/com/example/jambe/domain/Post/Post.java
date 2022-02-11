package com.example.jambe.domain.Post;

import com.example.jambe.domain.BaseTimeEntity;
import com.example.jambe.domain.Board.Board;
import com.example.jambe.domain.Member.Member;
import com.example.jambe.dto.Post.PostResponseDto;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class)
public class Post extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "POST_ID")
    private Long id;

    @Column(length=80, nullable = false)
    private String title;

    @ManyToOne
    @JoinColumn(name="MEMBER_ID")
    private Member member;

    @ManyToOne
    @JoinColumn(name="BOARD_ID")
    private Board board;

    @Column(length=100)
    private String content;

    @Builder
    Post(Long id,
         String title,
         String content) {

        this.id=id;
        this.title = title;
        this.content = content;
    }

    // board 연관관계 편의 메소드
    public void setBoard(Board board) {

        if(this.board != null) {
            this.board.getPosts().remove(this);
        }
        this.board = board;
        board.getPosts().add(this);
    }
    // member 연관관계 편의 메소드
    public void setMember(Member member) {

        if(this.member != null) {
            this.member.getPosts().remove(this);
        }
        this.member = member;
        member.getPosts().add(this);
    }
    //내용, 제목 수정
    public void update(String content, String title) {

        this.content = content;
        this.title = title;
    }

    static public PostResponseDto convertDto(Post post) {
        return PostResponseDto.builder()
                .id(post.getId())
                .board(post.getBoard())
                .content(post.getContent())
                .title(post.getTitle())
                .member(post.getMember())
                .build();
    }
}
