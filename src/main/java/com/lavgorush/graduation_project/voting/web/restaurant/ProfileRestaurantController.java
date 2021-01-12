package com.lavgorush.graduation_project.voting.web.restaurant;

import com.lavgorush.graduation_project.voting.model.Dish;
import com.lavgorush.graduation_project.voting.model.Restaurant;
import com.lavgorush.graduation_project.voting.to.RestaurantTo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(value = ProfileRestaurantController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@CacheConfig(cacheNames = "restaurants")
public class ProfileRestaurantController extends AbstractRestaurantController {
    public static final String REST_URL = "/api/profile/restaurants";

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<Restaurant> get(@PathVariable int id) {
        return super.get(id);
    }

    @Override
    @GetMapping
    public List<Restaurant> getAll() {
        return super.getAll();
    }

    @Override
    @GetMapping("/{id}/with-votes")
    public ResponseEntity<Restaurant> getWithVotes(@PathVariable int id) {
        return super.getWithVotes(id);
    }

    @Override
    @GetMapping("/{id}/with-votes-count")
    public ResponseEntity<RestaurantTo> getWithVotesCount(@PathVariable int id) {
        return super.getWithVotesCount(id);
    }

    @Override
    @GetMapping("/{id}/menu")
    public List<Dish> getCurrentLunchMenu(@PathVariable int id) {
        return super.getCurrentLunchMenu(id);
    }

    @Override
    @GetMapping("/{id}/menu/by")
    public List<Dish> getLunchMenuByDate(@PathVariable int id,
                                         @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return super.getLunchMenuByDate(id, date);
    }

    @Override
    @GetMapping("/{id}/dishes/{dish_id}")
    public ResponseEntity<Dish> getDish(@PathVariable int dish_id, @PathVariable int id) {
        return super.getDish(dish_id, id);
    }
}
