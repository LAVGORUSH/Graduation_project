package com.lavgorush.graduation_project.voting.util;

import com.lavgorush.graduation_project.voting.model.Dish;
import com.lavgorush.graduation_project.voting.model.Restaurant;
import com.lavgorush.graduation_project.voting.to.DishTo;
import com.lavgorush.graduation_project.voting.to.RestaurantTo;

import java.time.Clock;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class RestaurantUtil {
    public static Restaurant createNewFromTo(RestaurantTo restaurantTo) {
        return new Restaurant(null, restaurantTo.getName(), restaurantTo.getDescription());
    }

    public static RestaurantTo asTo(Restaurant restaurant) {
        return new RestaurantTo(restaurant.getId(), restaurant.getName(), restaurant.getDescription(), restaurant.getVotes().size());
    }

    public static Dish createNewDishFromTo(DishTo dishTo) {
        return new Dish(null, dishTo.getName(), dishTo.getPrice(), dishTo.getDatesOfUse());
    }

    public static DishTo asDishTo(Dish dish) {
        LocalDate lastLocalDate = dish.getDatesOfUse().stream()
                .max(LocalDate::compareTo)
                .orElse(LocalDate.now(Clock.systemDefaultZone()));
        return new DishTo(dish.getId(), dish.getName(), dish.getPrice(), lastLocalDate);
    }

    public static List<DishTo> asDishToList(List<Dish> dishes) {
        return dishes.stream()
                .map(RestaurantUtil::asDishTo)
                .collect(Collectors.toList());
    }
}
