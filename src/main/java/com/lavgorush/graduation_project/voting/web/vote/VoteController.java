package com.lavgorush.graduation_project.voting.web.vote;

import com.lavgorush.graduation_project.voting.exception.IllegalRequestDataException;
import com.lavgorush.graduation_project.voting.model.Restaurant;
import com.lavgorush.graduation_project.voting.model.User;
import com.lavgorush.graduation_project.voting.model.Vote;
import com.lavgorush.graduation_project.voting.repository.RestaurantRepository;
import com.lavgorush.graduation_project.voting.repository.UserRepository;
import com.lavgorush.graduation_project.voting.repository.VoteRepository;
import com.lavgorush.graduation_project.voting.web.AuthUser;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.Clock;
import java.time.LocalDateTime;
import java.time.LocalTime;

@RestController
@RequestMapping(value = VoteController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
public class VoteController {
    public static final String REST_URL = "/api/profile/votes";

    protected UserRepository userRepository;
    protected RestaurantRepository restaurantRepository;
    protected VoteRepository voteRepository;

    @Setter
    private Clock clock = Clock.systemDefaultZone();

    @PostMapping
    public ResponseEntity<Vote> vote(@RequestParam int id, @AuthenticationPrincipal AuthUser authUser) {
        Restaurant restaurant = restaurantRepository.getExisted(id);
        User user = userRepository.getExisted(authUser.id());

        LocalDateTime dateTimeOfVote = LocalDateTime.now(clock);

        Vote vote = voteRepository.getByUserIdAndDate(user.getId(), dateTimeOfVote.toLocalDate())
                .orElse(null);
        if (vote != null) {
            if (dateTimeOfVote.toLocalTime().isBefore(LocalTime.of(11, 0))) {
                log.info("User id={} change vote for restaurant with id={}", authUser.id(), id);
            } else {
                throw new IllegalRequestDataException("You can not change vote after 11:00");
            }
        } else {
            log.info("User id={} make new vote for restaurant with id={}", authUser.id(), id);
        }
        Vote created = voteRepository.save(new Vote(dateTimeOfVote.toLocalDate(), user, restaurant));

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

}
