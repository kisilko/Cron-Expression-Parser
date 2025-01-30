package com.github.kisilko.parser;

public class MonthParser extends AbstractCronFieldParser {

    private final static int MIN_VALUE = 1;
    private final static int MAX_VALUE = 12;

    public MonthParser() {
        super(MIN_VALUE, MAX_VALUE);
    }
}
