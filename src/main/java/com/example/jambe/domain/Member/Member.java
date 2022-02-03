package com.example.jambe.domain.Member;

import com.example.jambe.domain.BaseTimeEntity;
import com.example.jambe.domain.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Entity
public class Member extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @Builder
    public Member(Long id, String account,String name,String nickname,String email, String passwd, Role role) {
        this.id = id;
        this.account = account;
        this.name = name;
        this.nickname = nickname;
        this.email = email;
        this.passwd = passwd;
        this.role = role;
    }

    public String getRoleKey() {
        return this.role.getKey();
    }

    public Member update(String name, String email) {
        this.name = name;
        this.email = email;

        return this;
    }
}
