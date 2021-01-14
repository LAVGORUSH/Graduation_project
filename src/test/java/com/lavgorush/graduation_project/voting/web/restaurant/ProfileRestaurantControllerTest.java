package com.lavgorush.graduation_project.voting.web.restaurant;

import com.lavgorush.graduation_project.voting.RestaurantTestData;
import com.lavgorush.graduation_project.voting.util.RestaurantUtil;
import com.lavgorush.graduation_project.voting.web.AbstractControllerTest;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.lavgorush.graduation_project.voting.RestaurantTestData.*;
import static com.lavgorush.graduation_project.voting.TestUtil.userHttpBasic;
import static com.lavgorush.graduation_project.voting.UserTestData.user;
import static com.lavgorush.graduation_project.voting.util.RestaurantUtil.asDishTo;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ProfileRestaurantControllerTest extends AbstractControllerTest {
    private static final String REST_URL = ProfileRestaurantController.REST_URL + '/';

    @Test
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + RESTAURANT1_ID)
                .with(userHttpBasic(user)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(RESTAURANT_MATCHER.contentJson(restaurant1));
    }

    @Test
    void getAll() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL)
                .with(userHttpBasic(user)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(RESTAURANT_MATCHER.contentJson(restaurant3, restaurant2, restaurant1, restaurant4));
    }

    @Test
    void getWithVotes() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + RESTAURANT1_ID + "/with-votes")
                .with(userHttpBasic(user)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(RestaurantTestData.RESTAURANT_WITH_VOTES_MATCHER.contentJson(restaurant1));
    }

    @Test
    void getWithVotesCount() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + RESTAURANT1_ID + "/with-votes-count")
                .with(userHttpBasic(user)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(RESTAURANT_TO_MATCHER.contentJson(RestaurantUtil.asTo(restaurant1)));
    }

    @Test
    void getDish() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + RESTAURANT1_ID + "/dishes/" + DISH1_ID)
                .with(userHttpBasic(user)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(DISH_TO_MATCHER.contentJson(asDishTo(dish1)));
    }

    @Test
    void getCurrentLunchMenu() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + RESTAURANT1_ID + "/menu")
                .with(userHttpBasic(user)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(DISH_TO_MATCHER.contentJson(asDishTo(dish1), asDishTo(dish2)));
    }

    @Test
    void getLunchMenuByDate() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + RESTAURANT2_ID + "/menu/by")
                .param("date", "2021-01-05")
                .with(userHttpBasic(user)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(DISH_TO_MATCHER.contentJson(asDishTo(dish3), asDishTo(dish4)));
    }
}

