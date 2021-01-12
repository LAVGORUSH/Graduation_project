package com.lavgorush.graduation_project.voting;

import com.lavgorush.graduation_project.voting.model.Role;
import com.lavgorush.graduation_project.voting.model.User;
import com.lavgorush.graduation_project.voting.model.Vote;
import com.lavgorush.graduation_project.voting.util.JsonUtil;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class UserTestData {
    public static final TestMatcher<User> USER_MATCHER = TestMatcher.usingIgnoringFieldsComparator(User.class, "registered", "votes", "password");

    public static TestMatcher<User> USER_WITH_VOTES_MATCHER =
            TestMatcher.usingAssertions(User.class,
//     No need use ignoringAllOverriddenEquals, see https://assertj.github.io/doc/#breaking-changes
                    (a, e) -> assertThat(a).usingRecursiveComparison()
                            .ignoringFields("registered", "votes.user","votes.restaurant", "password").isEqualTo(e),
                    (a, e) -> {
                        throw new UnsupportedOperationException();
                    });

    public static final int USER_ID = 1;
    public static final int ADMIN_ID = 2;
    public static final int NOT_FOUND = 100;

    public static final User user = new User(USER_ID, "User", "user@yandex.ru", "password", Role.USER);
    public static final User admin = new User(ADMIN_ID, "Admin", "admin@gmail.com", "admin", Role.ADMIN, Role.USER);

    static {
        user.setVotes(List.of(new Vote(1, LocalDateTime.parse("2021-01-05T10:00:00"))));
        admin.setVotes(List.of(new Vote(2, LocalDateTime.parse("2021-01-05T10:00:00"))));
    }

    public static User getNew() {
        return new User(null, "New", "new@gmail.com", "newPass", true,new Date(), Collections.singleton(Role.USER));
    }

    public static User getUpdated() {
        User updated = new User(user);
        updated.setName("UpdatedName");
        updated.setPassword("newPass");
//        updated.setEnabled(false);/
        updated.setRoles(Collections.singletonList(Role.ADMIN));
        return updated;
    }

    public static String jsonWithPassword(User user, String passw) {
        return JsonUtil.writeAdditionProps(user, "password", passw);
    }
}
