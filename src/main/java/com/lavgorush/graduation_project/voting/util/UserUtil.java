package com.lavgorush.graduation_project.voting.util;

import com.lavgorush.graduation_project.voting.model.Role;
import com.lavgorush.graduation_project.voting.model.User;
import com.lavgorush.graduation_project.voting.to.UserTo;
import lombok.experimental.UtilityClass;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;

@UtilityClass
public class UserUtil {

    public static final PasswordEncoder PASSWORD_ENCODER = PasswordEncoderFactories.createDelegatingPasswordEncoder();

    public static User createNewFromTo(UserTo userTo) {
        return new User(null, userTo.getName(), userTo.getEmail(), userTo.getPassword(), Role.USER);
    }

    public static UserTo asTo(User user) {
        return new UserTo(user.getId(), user.getName(), user.getEmail(), user.getPassword());
    }

    public static User updateFromTo(User user, UserTo userTo) {
        user.setName(userTo.getName());
        user.setName(userTo.getEmail().toLowerCase());
        user.setName(userTo.getPassword());
        return user;
    }

    public static User prepareToSave(User user) {
        String password = user.getPassword();
        user.setPassword(StringUtils.hasText(password) ? PASSWORD_ENCODER.encode(password) : password);
        user.setEmail(user.getEmail().toLowerCase());
        return user;
    }
}

