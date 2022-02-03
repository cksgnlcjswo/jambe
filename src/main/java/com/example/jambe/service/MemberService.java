package com.example.jambe.service;

import com.example.jambe.domain.Member.Member;
import com.example.jambe.domain.Member.MemberRepository;
import com.example.jambe.dto.CustomIntegrationDto;
import com.example.jambe.dto.MemberDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class MemberService implements UserDetailsService {

    private final MemberRepository memberRepository;

    public Member joinUser(MemberDto memberDto) {
        // 비밀번호 암호화
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        memberDto.setPasswd(passwordEncoder.encode(memberDto.getPasswd()));

        return memberRepository.save(memberDto.toEntity());
    }

    @Override
    public UserDetails loadUserByUsername(String account) throws UsernameNotFoundException {
        Optional<Member> memberOptional = memberRepository.findByAccount(account);
        Member member = memberOptional.orElse(null);

        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(member.getRoleKey()));

        return new CustomIntegrationDto(member, member.getAccount(), member.getPasswd(), authorities);
    }
}
