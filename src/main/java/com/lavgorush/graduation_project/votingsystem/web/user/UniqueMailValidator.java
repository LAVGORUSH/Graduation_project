package com.lavgorush.graduation_project.votingsystem.web.user;

import com.lavgorush.graduation_project.votingsystem.HasIdAndEmail;
import com.lavgorush.graduation_project.votingsystem.repository.UserRepository;
import com.lavgorush.graduation_project.votingsystem.web.GlobalExceptionHandler;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@AllArgsConstructor
public class UniqueMailValidator implements Validator {
    private final UserRepository userRepository;

    @Override
    public boolean supports(Class<?> clazz) {
        return HasIdAndEmail.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        HasIdAndEmail user = (HasIdAndEmail) target;
        if (StringUtils.hasText(user.getEmail())) {
            if (userRepository.getByEmail(user.getEmail().toLowerCase())
                    .filter(u -> !u.getId().equals(user.getId())).isPresent()) {
                errors.rejectValue("email", "", GlobalExceptionHandler.EXCEPTION_DUPLICATE_EMAIL);
            }
        }
    }
}
