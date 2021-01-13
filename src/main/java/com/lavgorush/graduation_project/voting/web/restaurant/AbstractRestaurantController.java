package com.lavgorush.graduation_project.voting.web.restaurant;

import com.lavgorush.graduation_project.voting.model.Dish;
import com.lavgorush.graduation_project.voting.model.Restaurant;
import com.lavgorush.graduation_project.voting.repository.DishRepository;
import com.lavgorush.graduation_project.voting.repository.RestaurantRepository;
import com.lavgorush.graduation_project.voting.to.RestaurantTo;
import com.lavgorush.graduation_project.voting.util.RestaurantUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Slf4j
public abstract class AbstractRestaurantController {

    @Autowired
    protected RestaurantRepository restaurantRepository;

    @Autowired
    protected DishRepository dishRepository;

    public ResponseEntity<Restaurant> get(int id) {
        log.info("get {}", id);
        return ResponseEntity.of(restaurantRepository.findById(id));
    }

    public List<Restaurant> getAll() {
        log.info("getAll");
        return restaurantRepository.findAll(Sort.by(Sort.Direction.ASC, "name"));
    }

    public ResponseEntity<Restaurant> getWithVotes(int id) {
        log.info("getWithVotes {}", id);
        return ResponseEntity.of(restaurantRepository.getWithVotes(id));
    }

    public ResponseEntity<RestaurantTo> getWithVotesCount(int id) {
        log.info("getWithVotesCount {}", id);
        return ResponseEntity.of(Optional.of(RestaurantUtil.asTo(restaurantRepository.getWithVotes(id).get())));
    }

    public List<Dish> getCurrentLunchMenu(int id) {
        log.info("getCurrentLunchMenu for restaurant {}", id);
        return dishRepository.getMenuByDate(id, LocalDate.now());
    }

    public List<Dish> getLunchMenuByDate(int id, LocalDate date) {
        log.info("getLunchMenuByDate {} for restaurant {}", date, id);
        return dishRepository.getMenuByDate(id, date);
    }

    public ResponseEntity<Dish> getDish(int dish_id, int id) {
        log.info("getDish {}", id);
        return ResponseEntity.of(dishRepository.getByRestaurantId(dish_id, id));
    }
}
