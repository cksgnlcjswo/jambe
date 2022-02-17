package com.example.jambe.domain.Member;

import com.example.jambe.domain.BaseTimeEntity;
import com.example.jambe.domain.Comment.Comment;
import com.example.jambe.domain.Post.Post;
import com.example.jambe.domain.Role;
import com.example.jambe.dto.Member.MemberResponseDto;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class)
public class Member extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="MEMBER_ID")
    private Long id;

    @Column(length= 30)
    private String account;

    @Column(length= 30)
    private String name;

    @Column(length= 30)
    private String nickname;

    @Column(length = 30)
    private String email;

    @Column(length=100)
    private String passwd;

    @Enumerated(EnumType.STRING)
    @Column
    private Role role;

    @OneToMany(mappedBy = "member")
    private List<Post> posts = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<Comment> comments = new ArrayList<>();

    @Builder
    public Member(Long id,
                  String account,
                  String name,
                  String nickname,
                  String email,
                  String passwd) {

        this.id = id;
        this.account = account;
        this.name = name;
        this.nickname = nickname;
        this.email = email;
        this.passwd = passwd;
        this.role = Role.GUEST;
    }

    public String getRoleKey() {
        return this.role.getKey();
    }

    public Member update(String name, String email) {
        this.name = name;
        this.email = email;

        return this;
    }

    public static MemberResponseDto convertToResponseDto(Member member) {
        return MemberResponseDto.builder()
                .name(member.getName())
                .account(member.getAccount())
                .email(member.getEmail()).build();
    }
}
