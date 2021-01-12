package com.lavgorush.graduation_project.votingsystem.web.restaurant;

import com.lavgorush.graduation_project.votingsystem.repository.DishRepository;
import com.lavgorush.graduation_project.votingsystem.repository.RestaurantRepository;
import com.lavgorush.graduation_project.votingsystem.repository.VoteRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = RestaurantController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@CacheConfig(cacheNames = "restaurants")
@AllArgsConstructor
public class RestaurantController {
    public static final String REST_URL = "/api/account/restaurants";
    private RestaurantRepository restaurantRepository;
    private DishRepository dishRepository;
    private VoteRepository voteRepository;


}
