package com.github.kisilko.parser;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class DayOfMonthParserTest {

    private final CronFieldParser cronFieldParser = new DayOfMonthParser();

    static Stream<Arguments> validDataProvider() {
        return Stream.of(
                arguments("*", IntStream.rangeClosed(1, 31).boxed().toList()),
                arguments("*/1", IntStream.rangeClosed(1, 31).boxed().toList()),
                arguments("*/15", Arrays.asList(1, 16, 31)),
                arguments("1,3,30", Arrays.asList(1, 3, 30)),
                arguments("2-5", Arrays.asList(2, 3, 4, 5)),
                arguments("1", List.of(1))
        );
    }
    @ParameterizedTest
    @MethodSource("validDataProvider")
    void valid_input_test(String inputStr, List<Integer> expectedValues) {
        assertThat(cronFieldParser.parse(inputStr)).isEqualTo(expectedValues);
    }

    static Stream<Arguments> invalidDataProvider() {
        return Stream.of(
                arguments("**"),
                arguments("*/75"),
                arguments("1,70"),
                arguments("5-10-15"),
                arguments("70"),
                arguments("-1"),
                arguments("\\"),
                arguments("_1")
        );
    }
    @ParameterizedTest
    @MethodSource("invalidDataProvider")
    void invalid_input_test(String inputStr) {
        assertThrows(IllegalArgumentException.class, () -> {
            cronFieldParser.parse(inputStr);
        });
    }
}