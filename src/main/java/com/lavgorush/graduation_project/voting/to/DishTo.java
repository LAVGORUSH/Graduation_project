package com.lavgorush.graduation_project.voting.to;

import com.lavgorush.graduation_project.voting.HasId;
import lombok.EqualsAndHashCode;
import lombok.Value;
import org.hibernate.validator.constraints.SafeHtml;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

import static org.hibernate.validator.constraints.SafeHtml.WhiteListType.NONE;

@Value
@EqualsAndHashCode(callSuper = true)
public class DishTo extends BaseTo implements HasId, Serializable {

    @NotBlank
    @Size(min = 2, max = 100)
    @SafeHtml(whitelistType = NONE)
    String name;

    @DecimalMin(value = "0.00", inclusive = false)
    @Digits(integer = 5, fraction = 2)
    BigDecimal price;

    @NotNull
    LocalDate datesOfUse;

    public DishTo(Integer id, String name, BigDecimal price, LocalDate datesOfUse) {
        super(id);
        this.name = name;
        this.price = price;
        this.datesOfUse = datesOfUse;
    }
}
