package com.lavgorush.graduation_project.voting.web.vote;

import com.lavgorush.graduation_project.voting.exception.IllegalRequestDataException;
import com.lavgorush.graduation_project.voting.model.Vote;
import com.lavgorush.graduation_project.voting.repository.VoteRepository;
import com.lavgorush.graduation_project.voting.util.DateTimeUtil;
import com.lavgorush.graduation_project.voting.util.JsonUtil;
import com.lavgorush.graduation_project.voting.web.AbstractControllerTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static com.lavgorush.graduation_project.voting.TestUtil.readFromJson;
import static com.lavgorush.graduation_project.voting.TestUtil.userHttpBasic;
import static com.lavgorush.graduation_project.voting.UserTestData.admin;
import static com.lavgorush.graduation_project.voting.UserTestData.user;
import static com.lavgorush.graduation_project.voting.VoteTestData.*;
import static com.lavgorush.graduation_project.voting.util.DateTimeUtil.checkModificationAllowed;
import static com.lavgorush.graduation_project.voting.util.DateTimeUtil.getDateTimeOfVote;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class VoteControllerTest extends AbstractControllerTest {
    private static final String REST_URL = VoteController.REST_URL + '/';

    @Autowired
    VoteRepository voteRepository;

    @Test
    void createVote() throws Exception {
        Vote newVote = getNew();
        DateTimeUtil.setDateTimeOfVote(DateTimeUtil.getDateTimeOfVote().withHour(10));
        ResultActions action = perform(MockMvcRequestBuilders.post(REST_URL)
                .param("restaurantId", "1")
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(user))
                .content(JsonUtil.writeValue(newVote)))
                .andDo(print())
                .andExpect(status().isCreated());
        Vote created = readFromJson(action, Vote.class);
        int newId = created.id();
        newVote.setId(newId);
        VOTE_MATCHER.assertMatch(created, newVote);
        VOTE_MATCHER.assertMatch(voteRepository.getExisted(newId), newVote);
    }

    @Test
    void changeVoteBeforeTime() throws Exception {
        Vote updated = getUpdated();
        DateTimeUtil.setDateTimeOfVote(DateTimeUtil.getDateTimeOfVote().withHour(10));
        perform(MockMvcRequestBuilders.post(REST_URL)
                .param("restaurantId", "2")
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(user))
                .content(JsonUtil.writeValue(updated)))
                .andDo(print())
                .andExpect(status().isCreated());

        VOTE_MATCHER.assertMatch(voteRepository.getExisted(VOTE1_ID), getUpdated());
    }

    @Test
    void changeVoteAfterTime() throws Exception {
        Vote updated = getUpdated();
        DateTimeUtil.setDateTimeOfVote(DateTimeUtil.getDateTimeOfVote().withHour(12));
        perform(MockMvcRequestBuilders.post(REST_URL)
                .param("restaurantId", "2")
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(user))
                .content(JsonUtil.writeValue(updated)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
        assertThrows(IllegalRequestDataException.class,()->checkModificationAllowed(getDateTimeOfVote()));
    }

    @Test
    void getAllUserVote() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL)
                .with(userHttpBasic(admin)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(VOTE_MATCHER.contentJson(List.of(admin_vote1)));
    }

    @Test
    void getUserVote() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + VOTE1_ID)
                .with(userHttpBasic(user)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(VOTE_MATCHER.contentJson(user_vote1));
    }
}