package com.example.jambe.dto;

import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.io.Serializable;

public interface IntegrationMember extends OAuth2User, Serializable, UserDetails, CredentialsContainer {
}
