package com.lavgorush.graduation_project.voting;

import com.lavgorush.graduation_project.voting.model.Dish;
import com.lavgorush.graduation_project.voting.model.Restaurant;
import com.lavgorush.graduation_project.voting.model.Vote;
import com.lavgorush.graduation_project.voting.to.RestaurantTo;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class RestaurantTestData {
    public static final TestMatcher<Restaurant> RESTAURANT_MATCHER = TestMatcher.usingIgnoringFieldsComparator(Restaurant.class, "registered", "votes", "dishes");
    public static final TestMatcher<RestaurantTo> RESTAURANT_TO_MATCHER = TestMatcher.usingIgnoringFieldsComparator(RestaurantTo.class);
    public static final TestMatcher<Dish> DISH_MATCHER = TestMatcher.usingIgnoringFieldsComparator(Dish.class, "restaurant", "datesOfUse");

    public static TestMatcher<Restaurant> RESTAURANT_WITH_VOTES_MATCHER =
            TestMatcher.usingAssertions(Restaurant.class,
//     No need use ignoringAllOverriddenEquals, see https://assertj.github.io/doc/#breaking-changes
                    (a, e) -> assertThat(a).usingRecursiveComparison()
                            .ignoringFields("registered", "dishes", "votes.user", "votes.restaurant").isEqualTo(e),
                    (a, e) -> {
                        throw new UnsupportedOperationException();
                    });

    public static final int RESTAURANT1_ID = 1;
    public static final int RESTAURANT2_ID = 2;
    public static final int RESTAURANT3_ID = 3;
    public static final int RESTAURANT4_ID = 4;
    public static final int DISH1_ID = 1;
    public static final int DISH2_ID = 2;
    public static final int DISH3_ID = 3;
    public static final int DISH4_ID = 4;
    public static final int NOT_FOUND = 100;

    public static final Restaurant restaurant1 = new Restaurant(RESTAURANT1_ID, "Philibert", "French cuisine");
    public static final Restaurant restaurant2 = new Restaurant(RESTAURANT2_ID, "Palermo", "Italian cuisine");
    public static final Restaurant restaurant3 = new Restaurant(RESTAURANT3_ID, "Ditay", "Chinese cuisine");
    public static final Restaurant restaurant4 = new Restaurant(RESTAURANT4_ID, "Ronny", "Japanese cuisine");

    public static final Dish dish1 = new Dish(DISH1_ID, "Onion soup", BigDecimal.valueOf(410.00).setScale(2));
    public static final Dish dish2 = new Dish(DISH2_ID, "Salad", BigDecimal.valueOf(660.00).setScale(2));
    public static final Dish dish3 = new Dish(DISH3_ID, "Tomato soup", BigDecimal.valueOf(260.00).setScale(2));
    public static final Dish dish4 = new Dish(DISH4_ID, "Salad with chicken", BigDecimal.valueOf(290.00).setScale(2));


    static {
        restaurant1.setVotes(List.of(new Vote(1, LocalDateTime.parse("2021-01-05T10:00:00"))));
        restaurant2.setVotes(List.of(new Vote(2, LocalDateTime.parse("2021-01-05T10:00:00"))));
        dish1.setDatesOfUse(List.of(LocalDate.parse("2021-01-05"), LocalDate.now()));
        dish2.setDatesOfUse(List.of(LocalDate.parse("2021-01-05"), LocalDate.now()));
        dish3.setDatesOfUse(List.of(LocalDate.parse("2021-01-05")));
        dish4.setDatesOfUse(List.of(LocalDate.parse("2021-01-05")));
        restaurant1.setDishes(List.of(dish1, dish2));
    }
}
