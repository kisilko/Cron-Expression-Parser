package com.github.kisilko.parser;

public class MinuteParser extends AbstractCronFieldParser {

    private final static int MIN_VALUE = 0;
    private final static int MAX_VALUE = 59;

    public MinuteParser() {
        super(MIN_VALUE, MAX_VALUE);
    }
}
