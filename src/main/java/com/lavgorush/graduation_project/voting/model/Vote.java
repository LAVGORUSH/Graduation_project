package com.lavgorush.graduation_project.voting.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name = "votes", uniqueConstraints = {@UniqueConstraint(columnNames = {"date_time" ,"user_id"}, name = "votes_unique_datetime_user_idx")})
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(callSuper = true, exclude = {"user", "restaurant"})
public class Vote extends AbstractBaseEntity {

    @Column(name = "date_time", nullable = false)
    @NotNull
    private LocalDateTime dateTimeOfVote;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonBackReference(value = "user_votes")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    @JsonBackReference(value = "restaurant_votes")
    private Restaurant restaurant;


    public Vote(Integer id, LocalDateTime dateTimeOfVote) {
        super(id);
        this.dateTimeOfVote = dateTimeOfVote;
    }

    public LocalDate getDate() {
        return dateTimeOfVote.toLocalDate();
    }

    public LocalTime getTime() {
        return dateTimeOfVote.toLocalTime();
    }
}
