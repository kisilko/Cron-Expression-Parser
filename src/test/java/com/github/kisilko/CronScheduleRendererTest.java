package com.github.kisilko;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class CronScheduleRendererTest {

    @Test
    void renderer_is_producing_correct_string() {
        CronSchedule expectedSchedule = new CronSchedule(
                List.of(0, 15, 30, 45),
                List.of(0),
                List.of(1, 15),
                List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12),
                List.of(1, 2, 3, 4, 5),
                "/usr/bin/find"
        );
        var renderer = new CronScheduleRenderer(expectedSchedule);

        String expectedResult = """
                minute              0, 15, 30, 45
                hour                0
                day of month        1, 15
                month               1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12
                day of week         1, 2, 3, 4, 5
                command             /usr/bin/find
                """;
        assertThat(renderer.renderPlainText()).isEqualTo(expectedResult);
    }
}