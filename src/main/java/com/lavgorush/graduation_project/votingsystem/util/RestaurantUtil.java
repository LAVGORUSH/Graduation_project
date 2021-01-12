package com.lavgorush.graduation_project.votingsystem.util;

import com.lavgorush.graduation_project.votingsystem.model.Restaurant;
import com.lavgorush.graduation_project.votingsystem.to.RestaurantTo;

public class RestaurantUtil {
    public static Restaurant createNewFromTo(RestaurantTo restaurantTo) {
        return new Restaurant(null, restaurantTo.getName(), restaurantTo.getDescription());
    }

    public static RestaurantTo asTo(Restaurant restaurant) {
        return new RestaurantTo(restaurant.getId(), restaurant.getName(), restaurant.getDescription());
    }
}
