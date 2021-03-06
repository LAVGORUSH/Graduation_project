package com.lavgorush.graduation_project.voting.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.util.CollectionUtils;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "dishes", uniqueConstraints = {@UniqueConstraint(columnNames = {"name", "restaurant_id"}, name = "dishes_unique_name_restaurant_idx")})
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(callSuper = true, exclude = {"restaurant"})
public class Dish extends AbstractNamedEntity {

    @Column(name = "price", nullable = false)
    @DecimalMin(value = "0.00", inclusive = false)
    @Digits(integer = 5, fraction = 2)
    private BigDecimal price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    @JsonBackReference(value = "restaurant_dishes")
    private Restaurant restaurant;

    @CollectionTable(name = "dish_dates_use", joinColumns = @JoinColumn(name = "dish_id"),
            uniqueConstraints = {@UniqueConstraint(columnNames = {"dish_id", "date_use"}, name = "dish_dates_use_unique_idx")})
    @Column(name = "date_use")
    @ElementCollection(fetch = FetchType.EAGER)
    @JoinColumn(name = "dish_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Set<LocalDate> datesOfUse;

    public Dish(@NotNull Dish dish) {
        this(dish.getId(), dish.getName(), dish.getPrice(), dish.getDatesOfUse());
    }

    public Dish(Integer id, String name, BigDecimal price, Collection<LocalDate> datesOfUse) {
        super(id, name);
        this.price = price;
        if (!CollectionUtils.isEmpty(this.datesOfUse)) {
            datesOfUse.addAll(this.datesOfUse);
        }
        setDatesOfUse(datesOfUse);
    }

    public Dish(Integer id, String name, BigDecimal price, LocalDate... datesOfUse) {
        this(id, name, price, List.of(datesOfUse));

    }

    public void setDatesOfUse(Collection<LocalDate> datesOfUse) {
        this.datesOfUse = CollectionUtils.isEmpty(datesOfUse) ? Collections.emptySet() : Set.copyOf(datesOfUse);
    }
}
