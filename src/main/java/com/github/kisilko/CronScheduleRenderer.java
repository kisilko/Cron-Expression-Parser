package com.github.kisilko;

import java.util.List;
import java.util.stream.Collectors;

public class CronScheduleRenderer {

    private final CronSchedule cronSchedule;

    public CronScheduleRenderer(CronSchedule cronSchedule) {
        this.cronSchedule = cronSchedule;
    }

    public String renderPlainText() {
        return """
                minute              %s
                hour                %s
                day of month        %s
                month               %s
                day of week         %s
                command             %s
                """
            .formatted(
                    renderList(cronSchedule.minute()),
                    renderList(cronSchedule.hour()),
                    renderList(cronSchedule.dayOfMonth()),
                    renderList(cronSchedule.month()),
                    renderList(cronSchedule.dayOfWeek()),
                    cronSchedule.command()
            );
    }

    private String renderList(List<Integer> list) {
        return list.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(", "));
    }
}
