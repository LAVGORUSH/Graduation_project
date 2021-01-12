package com.lavgorush.graduation_project.voting.web;

import com.lavgorush.graduation_project.voting.model.User;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString(of = "user")
public class AuthUser extends org.springframework.security.core.userdetails.User {
    private final User user;

    public AuthUser(User user) {
        super(user.getEmail(), user.getPassword(), user.getRoles());
        this.user = user;
    }

    public int id() {
        return user.id();
    }
}
