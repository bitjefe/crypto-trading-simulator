package edu.depaul.cdm.se452.cryptotradingsimulator;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.geo.Metric;

import java.time.LocalDateTime;

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
        AdminMetric day1Item1 = createMetric("sign_up", LocalDateTime.of(2021, 10, 4, 0, 0));
        AdminMetric day1Item2 = createMetric("sign_up", LocalDateTime.of(2021, 10, 4, 0, 0));
        AdminMetric day1Item3 = createMetric("sign_up", LocalDateTime.of(2021, 10, 4, 0, 0));
        AdminMetric day2 = createMetric("sign_up", LocalDateTime.of(2021, 10, 5, 0, 0));
        AdminMetric day3 = createMetric("sign_up", LocalDateTime.of(2021, 10, 6, 0, 0));
        AdminMetric day4 = createMetric("sign_up", LocalDateTime.of(2021, 10, 7, 0, 0));
        AdminMetric day5 = createMetric("sign_up", LocalDateTime.of(2021, 10, 8, 0, 0));
        System.out.println("repository.findByFoo() = " + repository.findByFoo());
    }
}
