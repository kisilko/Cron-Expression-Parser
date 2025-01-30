package com.github.kisilko;

import com.github.kisilko.parser.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.params.provider.Arguments.arguments;

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

    @Test
    void it_parse_basic_cron_string_with_complex_command() {
        var cronString = "*/5 1,2,3 * * * echo hello world";

        CronSchedule actualSchedule = cronExpressionParser.parse(cronString);

        CronSchedule expectedSchedule = new CronSchedule(
                List.of(0, 5, 10, 15, 20, 25, 30, 35, 40, 45, 50, 55),
                List.of(1, 2, 3),
                IntStream.rangeClosed(1, 31).boxed().toList(),
                List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12),
                List.of(0, 1, 2, 3, 4, 5, 6),
                "echo hello world"
        );
        assertThat(actualSchedule).usingRecursiveComparison().isEqualTo(expectedSchedule);
    }

    static Stream<String> invalidDataProvider() {
        return Stream.of(
                "*/5 1,2,3 * * echo hello world",
                "0 0 1 13 * /usr/bin/find",
                "70 0 * * * /usr/bin/find",
                "* /5 * * * * /usr/bin/find",
                "A B C D E /usr/bin/find",
                "0 12 32 15 * /usr/bin/find",
                "*/9999 */9999 32-99 13-15 8-9-10-11-12-13-14-15-16 rm -rf /"
        );
    }
    @ParameterizedTest
    @MethodSource("invalidDataProvider")
    void invalid_cron_string_throws_exceptions(String cronString) {
        assertThrows(IllegalArgumentException.class, () -> {
            cronExpressionParser.parse(cronString);
        });
    }

}