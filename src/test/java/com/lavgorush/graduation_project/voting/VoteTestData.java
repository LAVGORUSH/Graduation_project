package com.lavgorush.graduation_project.voting;

import com.lavgorush.graduation_project.voting.model.Vote;

import java.time.Clock;
import java.time.LocalDate;

import static com.lavgorush.graduation_project.voting.RestaurantTestData.*;
import static com.lavgorush.graduation_project.voting.UserTestData.admin;
import static com.lavgorush.graduation_project.voting.UserTestData.user;
import static com.lavgorush.graduation_project.voting.util.DateTimeUtil.DEFAULT_DATE_OF_VOTE;

public class VoteTestData {
    public static final TestMatcher<Vote> VOTE_MATCHER = TestMatcher.usingIgnoringFieldsComparator(Vote.class, "user", "restaurant");

    public static final int VOTE1_ID = 1;
    public static final int VOTE2_ID = 2;

    public static final Vote user_vote1 = new Vote(VOTE1_ID, DEFAULT_DATE_OF_VOTE);
    public static final Vote admin_vote1 = new Vote(VOTE2_ID, DEFAULT_DATE_OF_VOTE);

    static {
        user_vote1.setUser(user);
        user_vote1.setRestaurant(restaurant1);
        admin_vote1.setUser(admin);
        admin_vote1.setRestaurant(restaurant2);
    }

    public static Vote getNew() {
        return new Vote(null, LocalDate.now(Clock.systemDefaultZone()), user, restaurant3);
    }

    public static Vote getUpdated() {
        return new Vote(user_vote1.getId(), user_vote1.getDateOfVote(), user_vote1.getUser(), restaurant2);
    }
}
