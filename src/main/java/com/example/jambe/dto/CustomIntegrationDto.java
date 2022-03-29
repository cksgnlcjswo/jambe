package com.example.jambe.dto;

import com.example.jambe.domain.Member.Member;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;

import java.util.*;

@Data
public class CustomIntegrationDto implements IntegrationMember{
    private Long id;
    private String name;
    private String passwd;
    private DefaultOAuth2User defaultOAuth2User;
    private Map<String, Object> attributes;
    private String nameAttributeKey;
    private final Set<GrantedAuthority> authorities;

    public CustomIntegrationDto(Member member, String username, String password, Collection<? extends GrantedAuthority> authorities) {
        new User(username, password, authorities);
        this.name = username;
        this.passwd = password;
        this.id = member.getId();
        this.authorities = Collections.unmodifiableSet(new LinkedHashSet<>(this.sortAuthorities(authorities)));
    }

    public CustomIntegrationDto(String username, Collection<? extends GrantedAuthority> authorities) {
        this.name = username;
        this.authorities = Collections.unmodifiableSet(new LinkedHashSet<>(this.sortAuthorities(authorities)));
    }

    public CustomIntegrationDto(Member member, Collection<? extends GrantedAuthority> authorities, Map<String, Object> attributes, String nameAttributeKey) {
        this.defaultOAuth2User = new DefaultOAuth2User(authorities, attributes, nameAttributeKey);
        this.name = member.getName();
        this.id = member.getId();
        this.authorities = Collections.unmodifiableSet(new LinkedHashSet<>(this.sortAuthorities(authorities)));
        this.attributes = Collections.unmodifiableMap(new LinkedHashMap<>(attributes));
        this.nameAttributeKey = nameAttributeKey;
    }

    private Set<GrantedAuthority> sortAuthorities(Collection<? extends GrantedAuthority> authorities) {
        SortedSet<GrantedAuthority> sortedAuthorities = new TreeSet<>(Comparator.comparing(GrantedAuthority::getAuthority));
        sortedAuthorities.addAll(authorities);
        return sortedAuthorities;
    }

    @Override
    public void eraseCredentials() {
        this.passwd = null;
    }

    @Override
    public String getPassword() {
        return this.passwd;
    }

    @Override
    public String getUsername() {
        return name;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public String getName() {
        return this.name;
    }
}
