package com.lavgorush.graduation_project.voting.web.restaurant;

import com.lavgorush.graduation_project.voting.model.Dish;
import com.lavgorush.graduation_project.voting.model.Restaurant;
import com.lavgorush.graduation_project.voting.repository.DishRepository;
import com.lavgorush.graduation_project.voting.repository.RestaurantRepository;
import com.lavgorush.graduation_project.voting.to.DishTo;
import com.lavgorush.graduation_project.voting.to.RestaurantTo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static com.lavgorush.graduation_project.voting.util.RestaurantUtil.*;

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
        return ResponseEntity.of(Optional.of(asTo(restaurantRepository.getWithVotes(id).get())));
    }

    public ResponseEntity<Dish> getDish(int dish_id, int id) {
        log.info("getDish {}", id);
        return ResponseEntity.of(dishRepository.getByRestaurantId(dish_id, id));
    }

    public ResponseEntity<DishTo> getDishTo(int dish_id, int id) {
        log.info("getDishTo {}", id);
        DishTo dishTo = asDishTo(dishRepository.getByRestaurantId(dish_id, id).get());
        return ResponseEntity.of(Optional.of(dishTo));
    }

    public List<DishTo> getCurrentLunchMenu(int id) {
        log.info("getCurrentLunchMenu for restaurant {}", id);
        return asDishToList(dishRepository.getMenuByDate(id, LocalDate.now()));
    }

    public List<DishTo> getLunchMenuByDate(int id, LocalDate date) {
        log.info("getLunchMenuByDate {} for restaurant {}", date, id);
        return asDishToList(dishRepository.getMenuByDate(id, date));
    }
}
