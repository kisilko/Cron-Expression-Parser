package com.github.kisilko.parser;

import java.util.List;

public abstract class AbstractCronFieldParser implements CronFieldParser {

    protected final int minValue;
    protected final int maxValue;
    protected AbstractCronFieldParser(int minValue, int maxValue) {
        this.minValue = minValue;
        this.maxValue = maxValue;
    }

    @Override
    public List<Integer> parse(String field) {
        return null;
    }
}
