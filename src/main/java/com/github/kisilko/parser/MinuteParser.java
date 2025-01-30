package com.github.kisilko.parser;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MinuteParser extends AbstractCronFieldParser {

    protected int minValue = 0;
    protected int maxValue = 59;

    public MinuteParser() {
        super(0, 59);
    }
}
