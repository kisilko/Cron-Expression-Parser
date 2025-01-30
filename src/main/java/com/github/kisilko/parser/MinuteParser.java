package com.github.kisilko.parser;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MinuteParser {

    protected int minValue = 0;
    protected int maxValue = 59;
    private final Pattern MINUTE_REGEX = Pattern.compile("^[0-9*/,-]+$");
    public List<Integer> parse(String minuteStr) {
        Matcher matcher = MINUTE_REGEX.matcher(minuteStr);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("Invalid minute field: " + minuteStr);
        }

        List<Integer> minutes = new ArrayList<>();

        if (minuteStr.equals("*")) {
            for (int i = 0; i <= 59; i++) {
                minutes.add(i);
            }
        }

        if (minuteStr.startsWith("*/")) {
            int interval = Integer.parseInt(minuteStr.substring(2));
            validateAllowedRange(interval);

            for (int i = 0; i <= 59; i += interval) {
                minutes.add(i);
            }
        }

        if (minuteStr.contains(",")) {
            String[] intervals = minuteStr.split(",");
            for (String interval : intervals) {
                int value = Integer.parseInt(interval);
                validateAllowedRange(value);
                minutes.add(value);
            }
        }

        if (minuteStr.contains("-")) {
            String[] intervals = minuteStr.split("-");
            validateRangeIntervalsCount(intervals);

            int start = Integer.parseInt(intervals[0]);
            int end = Integer.parseInt(intervals[1]);
            validateAllowedRange(start);
            validateAllowedRange(end);

            for (int i = start; i <= end; i++) {
                minutes.add(i);
            }
        }

        if (minutes.isEmpty()) {
            int value = Integer.parseInt(minuteStr);
            validateAllowedRange(value);
            minutes.add(value);
        }

        return minutes;
    }

    private void validateAllowedRange(int value) {
        if (value < minValue || value > maxValue) {
            throw new IllegalArgumentException("Value %d is out of range [%d, %d]".formatted(value, minValue, maxValue));
        }
    }

    private void validateRangeIntervalsCount(String[] intervals) {
        if (intervals.length != 2) {
            throw new IllegalArgumentException("Invalid range format");
        }
    }
}
