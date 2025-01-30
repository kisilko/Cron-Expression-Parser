package com.github.kisilko.parser;

public class HourParser extends AbstractCronFieldParser {

    private final static int MIN_VALUE = 0;
    private final static int MAX_VALUE = 23;

    public HourParser() {
        super(MIN_VALUE, MAX_VALUE);
    }
}
