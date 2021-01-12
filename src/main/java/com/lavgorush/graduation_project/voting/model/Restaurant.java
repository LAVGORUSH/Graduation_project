package com.lavgorush.graduation_project.voting.model;

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
@Table(name = "restaurants", uniqueConstraints = {@UniqueConstraint(columnNames = {"name", "description"}, name = "restaurants_unique_name_description_idx")})
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(callSuper = true, exclude = {"votes", "dishes"})
public class Restaurant extends AbstractNamedEntity {

    @Column(name = "description", nullable = false)
    @NotBlank
    @Size(min = 5, max = 120)
    @SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
    private String description;

    @Column(name = "enabled", nullable = false, columnDefinition = "bool default true")
    private boolean enabled = true;

    @Column(name = "registered", nullable = false, columnDefinition = "timestamp default now()")
    @NotNull
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Date registered = new Date();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "restaurant")
    @OrderBy("dateTimeOfVote desc")
    @JsonManagedReference(value = "restaurant_votes")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<Vote> votes;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "restaurant")
    @JsonManagedReference(value = "restaurant_dishes")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<Dish> dishes;

    public Restaurant(@NotNull Restaurant r) {
        this(r.getId(), r.getName(), r.getDescription(), r.isEnabled(), r.getRegistered());
    }

    public Restaurant(Integer id, String name, String description, boolean enabled, Date registered) {
        super(id, name);
        this.description = description;
        this.enabled = enabled;
        this.registered = registered;
    }

    public Restaurant(Integer id, String name, String description) {
        this(id, name, description, true, new Date());
    }
}
