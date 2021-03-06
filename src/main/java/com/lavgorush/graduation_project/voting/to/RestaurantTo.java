package com.lavgorush.graduation_project.voting.to;

import com.lavgorush.graduation_project.voting.HasId;
import lombok.EqualsAndHashCode;
import lombok.Value;
import org.hibernate.validator.constraints.SafeHtml;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.io.Serial;
import java.io.Serializable;

import static org.hibernate.validator.constraints.SafeHtml.WhiteListType.NONE;

@Value
@EqualsAndHashCode(callSuper = true)
public class RestaurantTo extends BaseTo implements HasId, Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @NotBlank
    @Size(min = 2, max = 100)
    @SafeHtml(whitelistType = NONE)
    String name;

    @NotBlank
    @Size(min = 5, max = 120)
    @SafeHtml(whitelistType = NONE)
    String description;

    @Positive
    int voteCount;

    public RestaurantTo(Integer id, String name, String description, int voteCount) {
        super(id);
        this.name = name;
        this.description = description;
        this.voteCount = voteCount;
    }

}
