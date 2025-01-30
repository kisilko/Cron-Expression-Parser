package com.github.kisilko;

import com.github.kisilko.parser.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CronExpressionParser {

    private final static Integer MAXIMUM_LENGTH_OF_CRON_STRING = 500;
    private final Pattern CRON_REGEX = Pattern.compile("""
            (?x)                                 # Free-spacing and comments regex mode
            ^                                    # Start of the string
            (?<minute>\\S{1,180})\\s             # Capture minute
            (?<hour>\\S{1,70})\\s                # Capture hour
            (?<dayOfMonth>\\S{1,100})\\s         # Capture dayOfMonth
            (?<month>\\S{1,30})\\s               # Capture month
            (?<dayOfWeek>\\S{1,15})\\s           # Capture dayOfWeek
            (?<command>.{1,500})                 # Capture command
            $                                    # End of the string
            """);

    private final MinuteParser minuteParser;
    private final HourParser hourParser;
    private final DayOfMonthParser dayOfMonthParser;
    private final MonthParser monthParser;
    private final DayOfWeekParser dayOfWeekParser;

    public CronExpressionParser(MinuteParser minuteParser, HourParser hourParser, DayOfMonthParser dayOfMonthParser,
                                MonthParser monthParser, DayOfWeekParser dayOfWeekParser) {
        this.minuteParser = minuteParser;
        this.hourParser = hourParser;
        this.dayOfMonthParser = dayOfMonthParser;
        this.monthParser = monthParser;
        this.dayOfWeekParser = dayOfWeekParser;
    }

    public CronSchedule parse(String cronStr) {
        if (cronStr == null || cronStr.isEmpty() || cronStr.length() > MAXIMUM_LENGTH_OF_CRON_STRING) {
            throw new IllegalArgumentException("Invalid cron expression: " + cronStr);
        }

        Matcher matcher = CRON_REGEX.matcher(cronStr);
        if (matcher.matches()) {
            return new CronSchedule(
                minuteParser.parse(matcher.group("minute")),
                hourParser.parse(matcher.group("hour")),
                dayOfMonthParser.parse(matcher.group("dayOfMonth")),
                monthParser.parse(matcher.group("month")),
                dayOfWeekParser.parse(matcher.group("dayOfWeek")),
                matcher.group("command")
            );
        } else {
            throw new IllegalArgumentException("Invalid format of expression: " + cronStr);
        }
    }
}
