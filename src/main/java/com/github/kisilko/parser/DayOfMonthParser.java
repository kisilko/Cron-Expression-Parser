package com.github.kisilko.parser;


public class DayOfMonthParser extends AbstractCronFieldParser {

    private final static int MIN_VALUE = 1;
    private final static int MAX_VALUE = 31;

    public DayOfMonthParser() {
        super(MIN_VALUE, MAX_VALUE);
    }
}
