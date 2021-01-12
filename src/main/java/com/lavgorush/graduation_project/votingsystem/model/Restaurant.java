package com.lavgorush.graduation_project.votingsystem.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.validator.constraints.SafeHtml;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "restaurants", uniqueConstraints = {@UniqueConstraint(columnNames = {"name", "address"}, name = "restaurants_unique_name_address_idx")})
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(callSuper = true, exclude = {"votes", "dishes"})
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class Restaurant extends AbstractNamedEntity {

    @Column(name = "description", nullable = false)
    @NotBlank
    @Size(min = 5, max = 120)
    @SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
    private String description;

    @Column(name = "registered", nullable = false, columnDefinition = "timestamp default now()")
    @NotNull
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Date registered = new Date();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "vote")
    @OrderBy("dateTime desc")
    @JsonManagedReference
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<Vote> votes;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "dish")
    @OrderBy("date desc")
    @JsonManagedReference
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<Dish> dishes;

    public Restaurant(Integer id, String name, String description, Date registered) {
        super(id, name);
        this.description = description;
        this.registered = registered;
    }

    public Restaurant(Integer id, String name, String description) {
        this(id, name, description, new Date());
    }

    public Restaurant(Restaurant restaurant) {
        this(restaurant.getId(), restaurant.getName(), restaurant.getDescription(), restaurant.getRegistered());
    }
}
