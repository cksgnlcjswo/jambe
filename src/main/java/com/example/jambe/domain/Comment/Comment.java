package com.example.jambe.domain.Comment;

import com.example.jambe.domain.BaseTimeEntity;
import com.example.jambe.domain.Board.Board;
import com.example.jambe.domain.Member.Member;
import com.example.jambe.domain.Post.Post;
import com.example.jambe.dto.Comment.CommentResponseDto;
import lombok.Builder;
import lombok.Generated;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Getter
@NoArgsConstructor
@Entity
public class Comment extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="COMMENT_ID")
    private Long id;

    @Column(length=80,nullable = false)
    private String content;

    @ManyToOne
    @JoinColumn(name="POST_ID")
    private Post post;

    @ManyToOne
    @JoinColumn(name="MEMBER_ID")
    private Member member;

    @Builder
    public Comment(Long id, String content) {
        this.id = id;
        this.content = content;
    }

    // post 연관관계 편의 메소드
    public void setPost(Post post) {

        if(this.post != null) {
            this.post.getComments().remove(this);
        }
        this.post = post;
        post.getComments().add(this);
    }
    // member 연관관계 편의 메소드
    public void setMember(Member member) {

        if(this.member != null) {
            this.member.getComments().remove(this);
        }
        this.member = member;
        member.getComments().add(this);
    }

    public static CommentResponseDto convertResponseDto(Comment comment) {
        return CommentResponseDto.builder()
                .content(comment.getContent())
                .member(comment.getMember().getName()).build();
    }
}
