package com.lavgorush.graduation_project.voting.web.restaurant;

import com.lavgorush.graduation_project.voting.model.Dish;
import com.lavgorush.graduation_project.voting.model.Restaurant;
import com.lavgorush.graduation_project.voting.to.RestaurantTo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.time.LocalDate;
import java.util.List;

import static com.lavgorush.graduation_project.voting.util.ValidationUtil.checkNew;
import static com.lavgorush.graduation_project.voting.util.ValidationUtil.checkSingleModification;

@RestController
@RequestMapping(value = AdminRestaurantController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@CacheConfig(cacheNames = "restaurants")
public class AdminRestaurantController extends AbstractRestaurantController {
    public static final String REST_URL = "/api/admin/restaurants";

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

    @GetMapping("/{id}/dishes")
    public List<Dish> getAllDish(@PathVariable int id) {
        return dishRepository.getAllByRestaurantId(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @CacheEvict(value = "restaurants", allEntries = true)
    public void delete(@PathVariable int id) {
        log.info("delete {}", id);
        checkSingleModification(restaurantRepository.delete(id), "Restaurant id=" + id + " not found");
    }

    @DeleteMapping("/{id}/dishes/{dish_id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @CacheEvict(value = "restaurants", allEntries = true)
    public void deleteDish(@PathVariable int dish_id, @PathVariable int id) {
        log.info("delete dish {} in the restaurant {}", dish_id, id);
        checkSingleModification(dishRepository.delete(dish_id, id), "In the restaurant with id=" + id + "dish id=" + " not found");
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @CacheEvict(cacheNames = "restaurants", allEntries = true)
    public ResponseEntity<Restaurant> createWithLocation(@Valid @RequestBody Restaurant restaurant) {
        log.info("create {}", restaurant);
        checkNew(restaurant);
        Restaurant created = restaurantRepository.save(restaurant);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/users/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PostMapping(value = "/{id}/dishes", consumes = MediaType.APPLICATION_JSON_VALUE)
    @CacheEvict(cacheNames = "restaurants", allEntries = true)
    public ResponseEntity<Dish> createDishWithLocation(@Valid @RequestBody Dish dish, @PathVariable int id) {
        log.info("create dish {} in the restaurant {}", dish, id);
        checkNew(dish);
        Restaurant found = restaurantRepository.getExisted(id);
        dish.setRestaurant(found);
        Dish created = dishRepository.save(dish);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/users/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @CacheEvict(cacheNames = "restaurants", allEntries = true)
    public void update(@Valid @RequestBody Restaurant restaurant, @PathVariable int id) throws BindException {
        log.info("update {} with id={}", restaurant, id);
        restaurantRepository.save(restaurant);
    }

    @PutMapping(value = "/{id}/dishes/{dish_id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @CacheEvict(cacheNames = "restaurants", allEntries = true)
    public void updateDish(@RequestBody Dish dish, @PathVariable int id, @PathVariable int dish_id) throws BindException {
        log.info("update dish {} with id={} in the restaurant {}", dish, dish_id, id);
        Restaurant found = restaurantRepository.getExisted(id);
        dish.setRestaurant(found);
        dishRepository.save(dish);
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Transactional
    @CacheEvict(cacheNames = "restaurants", allEntries = true)
    public void enable(@PathVariable int id, @RequestParam boolean enabled) {
        log.info(enabled ? "enable {}" : "disable {}", id);
        Restaurant restaurant = restaurantRepository.getExisted(id);
        restaurant.setEnabled(enabled);
    }
}
