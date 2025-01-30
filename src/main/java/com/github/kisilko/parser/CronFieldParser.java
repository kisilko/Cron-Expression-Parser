package com.github.kisilko.parser;

import java.util.List;

public interface CronFieldParser {

    List<Integer> parse(String field);
}
