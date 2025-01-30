package com.github.kisilko.parser;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class AbstractCronFieldParser implements CronFieldParser {

    private final Pattern FIELD_REGEX = Pattern.compile("^[0-9*/,-]+$");
    private final int minValue;
    private final int maxValue;
    protected AbstractCronFieldParser(int minValue, int maxValue) {
        this.minValue = minValue;
        this.maxValue = maxValue;
    }

    @Override
    public List<Integer> parse(String fieldStr) {
        validateFieldContent(fieldStr);

        List<Integer> minutes = new ArrayList<>();

        if (fieldStr.equals("*")) {
            for (int i = minValue; i <= maxValue; i++) {
                minutes.add(i);
            }
        }

        if (fieldStr.startsWith("*/")) {
            int interval = Integer.parseInt(fieldStr.substring(2));
            validateAllowedRange(interval);

            for (int i = minValue; i <= maxValue; i += interval) {
                minutes.add(i);
            }
        }

        if (fieldStr.contains(",")) {
            String[] intervals = fieldStr.split(",");
            for (String interval : intervals) {
                int value = Integer.parseInt(interval);
                validateAllowedRange(value);
                minutes.add(value);
            }
        }

        if (fieldStr.contains("-")) {
            String[] intervals = fieldStr.split("-");
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
            int value = Integer.parseInt(fieldStr);
            validateAllowedRange(value);
            minutes.add(value);
        }

        return minutes;
    }

    private void validateFieldContent(String fieldStr) {
        Matcher matcher = FIELD_REGEX.matcher(fieldStr);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("Invalid field: " + fieldStr);
        }
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
