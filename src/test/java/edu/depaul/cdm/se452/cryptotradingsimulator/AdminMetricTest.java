package edu.depaul.cdm.se452.cryptotradingsimulator;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class AdminMetricTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private AdminMetricRepository repository;

    @Test
    void canQueryMetrics() {
        AdminMetric m = createMetric("sign_up", LocalDateTime.of(2021, 10, 4, 0, 0));
        assertThat(repository.findById(m.getId()).isPresent()).isTrue();
    }

    private AdminMetric createMetric(String name, LocalDateTime createdAt) {
        AdminMetric m = new AdminMetric();
        m.setName(name);
        m.setCreatedAt(createdAt);
        entityManager.persistAndFlush(m);
        return m;
    }

    @Test
    void canGroupByDay() {
        LocalDateTime day1 = LocalDateTime.of(2021, 10, 4, 0, 0);
        createMetric("sign_up", day1);
        createMetric("sign_up", day1);
        createMetric("sign_up", day1);

        // Day 2
        LocalDateTime day2 = LocalDateTime.of(2021, 10, 5, 0, 0);
        createMetric("sign_up",  day2);
        createMetric("sign_up", day2.plusHours(12));

        // Out of range date
        LocalDateTime day3 = LocalDateTime.of(2021, 10, 1, 0, 13);
        createMetric("sign_up", day3);

        List<AdminMetric> metricCountResult = repository.findAllWithCreatedAtAfter(LocalDateTime.of(2021, 10, 2, 0, 0));
        HashMap<LocalDate, List<AdminMetric>> groupedResults = AdminMetric.groupByDay(metricCountResult);
        assertThat(groupedResults.get(day1.toLocalDate()).size()).isEqualTo(3);
        assertThat(groupedResults.get(day2.toLocalDate()).size()).isEqualTo(2);
        assertThat(groupedResults.get(day3.toLocalDate())).isNullOrEmpty();
        AdminMetric.groupByDay(metricCountResult);
    }
}
