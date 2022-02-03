package com.example.jambe.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Role {

    ADMIN("ROLE_ADMIN", "admin"),
    GUEST("ROLE_GUEST", "guest");

    private String key;
    private String title;
}
