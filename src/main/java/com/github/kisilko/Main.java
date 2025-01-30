package com.github.kisilko;

import com.github.kisilko.parser.*;

public class Main {
    public static void main(String[] args) {
        var cronExpressionParser = new CronExpressionParser(
                new MinuteParser(),
                new HourParser(),
                new DayOfMonthParser(),
                new MonthParser(),
                new DayOfWeekParser()
        );

        try {
            cronExpressionParser.parse(args[0]);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }
}