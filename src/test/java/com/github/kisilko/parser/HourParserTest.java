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

class HourParserTest {
    private final CronFieldParser cronFieldParser = new HourParser();

    static Stream<Arguments> validDataProvider() {
        return Stream.of(
                arguments("*", IntStream.rangeClosed(0, 23).boxed().toList()),
                arguments("*/1", IntStream.rangeClosed(0, 23).boxed().toList()),
                arguments("*/3", Arrays.asList(0, 3, 6, 9, 12, 15, 18, 21)),
                arguments("1,5,9", Arrays.asList(1, 5, 9)),
                arguments("5-10", Arrays.asList(5, 6, 7, 8, 9, 10)),
                arguments("22", List.of(22))
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
                arguments("*/24"),
                arguments("1,20,24"),
                arguments("5-10-15"),
                arguments("066"),
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