package com.github.kisilko;

import com.github.kisilko.parser.*;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class CronExpressionParserTest {

    private final CronExpressionParser cronExpressionParser = new CronExpressionParser(
            new MinuteParser(),
            new HourParser(),
            new DayOfMonthParser(),
            new MonthParser(),
            new DayOfWeekParser()
    );

    @Test
    void it_parse_basic_cron_string() {
        var cronString = "*/15 0 1,15 * 1-5 /usr/bin/find";

        CronSchedule actualSchedule = cronExpressionParser.parse(cronString);

        CronSchedule expectedSchedule = new CronSchedule(
                List.of(0, 15, 30, 45),
                List.of(0),
                List.of(1, 15),
                List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12),
                List.of(1, 2, 3, 4, 5),
                "/usr/bin/find"
        );
        assertThat(actualSchedule).usingRecursiveComparison().isEqualTo(expectedSchedule);
    }

}