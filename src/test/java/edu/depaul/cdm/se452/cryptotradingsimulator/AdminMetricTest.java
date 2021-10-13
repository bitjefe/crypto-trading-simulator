package edu.depaul.cdm.se452.cryptotradingsimulator;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.mock.mockito.MockBean;

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

    @MockBean(name = "printCacheItems")
    CommandLineRunner mongoMock;

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
        System.out.println("--- Admin Metrics ---");
        System.out.println("--- three users sign up on the first day ---");
        LocalDateTime day1 = LocalDateTime.of(2021, 10, 4, 0, 0);
        createMetric("sign_up", day1);
        createMetric("sign_up", day1);
        createMetric("sign_up", day1);

        // Day 2
        System.out.println("--- two users sign up on the second day ---");
        LocalDateTime day2 = LocalDateTime.of(2021, 10, 5, 0, 0);
        createMetric("sign_up",  day2);
        createMetric("sign_up", day2.plusHours(12));

        // Out of range date
        System.out.println("--- one user signs up on a day outside of our date range filter ---");
        LocalDateTime day3 = LocalDateTime.of(2021, 10, 1, 0, 13);
        createMetric("sign_up", day3);

        List<AdminMetric> metricCountResult = repository.findAllWithCreatedAtAfter(LocalDateTime.of(2021, 10, 2, 0, 0));
        HashMap<LocalDate, List<AdminMetric>> groupedResults = AdminMetric.groupByDay(metricCountResult);
        System.out.println("--- querying metrics fired over the past week, grouped by day ---");
        System.out.println(day1);
        groupedResults.get(day1.toLocalDate()).forEach(System.out::println);
        System.out.println(day2);
        groupedResults.get(day2.toLocalDate()).forEach(System.out::println);
        assertThat(groupedResults.get(day1.toLocalDate()).size()).isEqualTo(3);
        assertThat(groupedResults.get(day2.toLocalDate()).size()).isEqualTo(2);
        assertThat(groupedResults.get(day3.toLocalDate())).isNullOrEmpty();
    }
}
