package com.lavgorush.graduation_project.voting.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Table(name = "votes", uniqueConstraints = {@UniqueConstraint(columnNames = {"date", "user_id"}, name = "votes_unique_date_user_idx")})
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(callSuper = true, exclude = {"user", "restaurant"})
public class Vote extends AbstractBaseEntity {

    @Column(name = "date", nullable = false)
    @NotNull
    private LocalDate dateOfVote;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonBackReference(value = "user_votes")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    @JsonBackReference(value = "restaurant_votes")
    private Restaurant restaurant;

    public Vote(LocalDate dateOfVote, User user, Restaurant restaurant) {
        this(null, dateOfVote, user, restaurant);
    }

    public Vote(Integer id, LocalDate dateOfVote, User user, Restaurant restaurant) {
        super(id);
        this.dateOfVote = dateOfVote;
        this.user = user;
        this.restaurant = restaurant;
    }

    public Vote(Integer id, LocalDate dateOfVote) {
        super(id);
        this.dateOfVote = dateOfVote;
    }
}
