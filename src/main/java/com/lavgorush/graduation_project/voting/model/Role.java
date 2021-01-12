package com.lavgorush.graduation_project.voting.model;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    ADMIN, USER;

    @Override
    public String getAuthority() {
        return "ROLE_" + name();
    }
}
