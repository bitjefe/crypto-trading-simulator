package edu.depaul.cdm.se452.cryptotradingsimulator;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Data
@Entity
public class AdminMetric {
    @Id
    @GeneratedValue
    private long id;

    private String name;

    private LocalDateTime createdAt;

    private LocalDate createdAtDay() {
        return this.createdAt.toLocalDate();
    }

    public static HashMap<LocalDate, List<AdminMetric>> groupByDay(List<AdminMetric> metrics) {
        HashMap<LocalDate, List<AdminMetric>> groupedMetrics = new HashMap<>();
        metrics.forEach(m -> {
            if (!groupedMetrics.containsKey(m.createdAtDay())) {
                groupedMetrics.put(m.createdAtDay(), new ArrayList<>());
            }

            List<AdminMetric> metricsByDay = groupedMetrics.get(m.createdAtDay());
            metricsByDay.add(m);
            groupedMetrics.put(m.createdAtDay(), metricsByDay);
        });

        return groupedMetrics;
    }
}
