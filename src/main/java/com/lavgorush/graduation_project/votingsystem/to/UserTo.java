package com.lavgorush.graduation_project.votingsystem.to;

import com.lavgorush.graduation_project.votingsystem.HasIdAndEmail;
import lombok.EqualsAndHashCode;
import lombok.Value;
import org.hibernate.validator.constraints.SafeHtml;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serial;
import java.io.Serializable;

import static org.hibernate.validator.constraints.SafeHtml.WhiteListType.NONE;

@Value
@EqualsAndHashCode(callSuper = true)
public class UserTo extends BaseTo implements HasIdAndEmail, Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @NotBlank
    @Size(min = 2, max = 100)
    @SafeHtml(whitelistType = NONE)
    String name;

    @Email
    @NotBlank
    @Size(max = 100)
    @SafeHtml(whitelistType = NONE)
    String email;

    @NotBlank
    @Size(min = 2, max = 32)
    String password;

    public UserTo(Integer id, String name, String email, String password) {
        super(id);
        this.name = name;
        this.email = email;
        this.password = password;
    }
}
