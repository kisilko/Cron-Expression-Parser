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

class MonthParserTest {

    private final CronFieldParser cronFieldParser = new MonthParser();

    static Stream<Arguments> validDataProvider() {
        return Stream.of(
                arguments("*", IntStream.rangeClosed(1, 12).boxed().toList()),
                arguments("*/1", IntStream.rangeClosed(1, 12).boxed().toList()),
                arguments("*/3", Arrays.asList(1, 4, 7, 10)),
                arguments("1,5,9", Arrays.asList(1, 5, 9)),
                arguments("5-10", Arrays.asList(5, 6, 7, 8, 9, 10)),
                arguments("4", List.of(4))
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
                arguments("*/13"),
                arguments("1,20"),
                arguments("5-10-15"),
                arguments("60"),
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