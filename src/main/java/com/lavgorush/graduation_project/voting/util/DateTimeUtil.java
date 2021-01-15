package com.lavgorush.graduation_project.voting.util;

import com.lavgorush.graduation_project.voting.exception.IllegalRequestDataException;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.UtilityClass;

import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@UtilityClass
public class DateTimeUtil {

    public static final LocalTime DEADLINE = LocalTime.of(11, 0);
    @Setter
    private static Clock clock = Clock.systemDefaultZone();

    @Getter
    @Setter
    private static LocalDateTime dateTimeOfVote = LocalDateTime.now(clock);

    public static final LocalDate DEFAULT_DATE_OF_VOTE = getDateTimeOfVote().toLocalDate();

    public static void checkModificationAllowed(LocalDateTime dateTime) {
        if (dateTime.toLocalTime().isAfter(DEADLINE)) {
            throw new IllegalRequestDataException("You can not change vote after " + DEADLINE);
        }
    }
}
