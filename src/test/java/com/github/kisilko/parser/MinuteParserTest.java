package com.github.kisilko.parser;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class MinuteParserTest {

    private final MinuteParser minuteParser = new MinuteParser();

    static Stream<Arguments> validDataProvider() {
        return Stream.of(
            arguments("*", IntStream.rangeClosed(0, 59).boxed().toList()),
            arguments("*/1", IntStream.rangeClosed(0, 59).boxed().toList()),
            arguments("*/15", Arrays.asList(0, 15, 30, 45)),
            arguments("1,15", Arrays.asList(1, 15)),
            arguments("5-10", Arrays.asList(5, 6, 7, 8, 9, 10)),
            arguments("45", List.of(45))
        );
    }
    @ParameterizedTest
    @MethodSource("validDataProvider")
    void valid_input_test(String inputStr, List<Integer> expectedMinutes) {
        assertThat(minuteParser.parse(inputStr)).isEqualTo(expectedMinutes);
    }

    static Stream<Arguments> invalidDataProvider() {
        return Stream.of(
                arguments("**"),
                arguments("*/75"),
                arguments("1,70"),
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
            minuteParser.parse(inputStr);
        });
    }
}