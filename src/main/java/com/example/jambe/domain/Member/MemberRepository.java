package com.example.jambe.domain.Member;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member,Long> {

    Optional<Member> findByName(String username);
    Optional<Member> findByAccount(String account);
}
