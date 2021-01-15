package com.lavgorush.graduation_project.voting.web.vote;

import com.lavgorush.graduation_project.voting.exception.NotFoundException;
import com.lavgorush.graduation_project.voting.model.Restaurant;
import com.lavgorush.graduation_project.voting.model.User;
import com.lavgorush.graduation_project.voting.model.Vote;
import com.lavgorush.graduation_project.voting.repository.RestaurantRepository;
import com.lavgorush.graduation_project.voting.repository.UserRepository;
import com.lavgorush.graduation_project.voting.repository.VoteRepository;
import com.lavgorush.graduation_project.voting.util.DateTimeUtil;
import com.lavgorush.graduation_project.voting.web.AuthUser;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.Clock;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static com.lavgorush.graduation_project.voting.util.DateTimeUtil.checkModificationAllowed;

@RestController
@RequestMapping(value = VoteController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
public class VoteController {
    public static final String REST_URL = "/api/profile/votes";
    @Autowired
    protected UserRepository userRepository;

    @Autowired
    protected RestaurantRepository restaurantRepository;

    @Autowired
    protected VoteRepository voteRepository;

    @Setter
    private static Clock clock = Clock.systemDefaultZone();

    @PostMapping
    @CacheEvict(value = {"restaurants", "users"}, allEntries = true)
    public ResponseEntity<Vote> vote(@RequestParam int restaurantId,
                                     @AuthenticationPrincipal AuthUser authUser) {
        Restaurant restaurant = restaurantRepository.getExisted(restaurantId);
        User user = userRepository.getExisted(authUser.id());

        LocalDateTime dateTimeOfVote = DateTimeUtil.getDateTimeOfVote();

        Vote vote = voteRepository.getByUserIdAndDate(user.id(), dateTimeOfVote.toLocalDate()).orElse(null);

        if (vote != null) {
            checkModificationAllowed(dateTimeOfVote);
            log.info("User id={} change vote for restaurant with id={}", authUser.id(), restaurantId);
            vote.setRestaurant(restaurant);

        } else {
            log.info("User id={} make new vote for restaurant with id={}", authUser.id(), restaurantId);
            vote = new Vote(dateTimeOfVote.toLocalDate(), user, restaurant);
        }

        Vote created = voteRepository.save(vote);

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @GetMapping("/")
    public List<Vote> getAllUserVote(@AuthenticationPrincipal AuthUser authUser) {
        log.info("Get all votes by user id={}", authUser.id());
        return voteRepository.getAllByUserId(authUser.id());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Vote> getUserVote(@PathVariable int id, @AuthenticationPrincipal AuthUser authUser) {
        Vote found = voteRepository.getByIdAndUserId(id, authUser.id())
                .orElseThrow(() -> new NotFoundException("Vote with id= " + id + " not found"));
        log.info("Get vote with id={} by user id={}", id, authUser.id());
        return ResponseEntity.of(Optional.of(found));
    }
}
