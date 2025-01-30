package com.github.kisilko.parser;

public class DayOfWeekParser extends AbstractCronFieldParser {

    private final static int MIN_VALUE = 0;
    private final static int MAX_VALUE = 6;

    public DayOfWeekParser() {
        super(MIN_VALUE, MAX_VALUE);
    }
}
