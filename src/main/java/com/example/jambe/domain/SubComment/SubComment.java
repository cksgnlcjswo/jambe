package com.example.jambe.domain.SubComment;

import com.example.jambe.domain.Comment.Comment;
import com.example.jambe.domain.Member.Member;
import com.example.jambe.domain.Post.Post;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Entity
public class SubComment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="SUBCOMMENT_ID")
    private Long id;

    @Column(length = 30, nullable = false)
    private String content;

    @ManyToOne
    @JoinColumn(name="MEMBER_ID")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "COMMENT_ID")
    private Comment comment;

    public void setComment(Comment comment) {

        if(this.comment != null) {
            this.comment.getSubComments().remove(this);
        }
        this.comment = comment;
        comment.getSubComments().add(this);
    }
    // member 연관관계 편의 메소드
    public void setMember(Member member) {

        if(this.member != null) {
            this.member.getComments().remove(this);
        }
        this.member = member;
        member.getSubComments().add(this);
    }

    @Builder
    public SubComment(Long id, String content) {
        this.id = id;
        this.content = content;
    }
}
