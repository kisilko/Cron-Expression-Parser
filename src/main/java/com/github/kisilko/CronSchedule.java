package com.github.kisilko;

import java.util.List;

public record CronSchedule(
        List<Integer> minute,
        List<Integer> hour,
        List<Integer> dayOfMonth,
        List<Integer> month,
        List<Integer> dayOfWeek,
        String command
) {}
