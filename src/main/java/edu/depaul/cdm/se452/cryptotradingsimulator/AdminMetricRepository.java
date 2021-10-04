package edu.depaul.cdm.se452.cryptotradingsimulator;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import javax.persistence.NamedQuery;
import java.util.List;

/**
 * For Pagination and Other JPA functionality beyond base CRUD services
 * @see https://docs.spring.io/spring-data/data-jpa/docs/current/api/org/springframework/data/jpa/repository/JpaRepository.html
 */
public interface AdminMetricRepository extends CrudRepository<AdminMetric, Long>  {
    public List<AdminMetric> findByName(String name);

    @Query("select u from AdminMetric u where u.name = 'sign_in'")
    public AdminMetric findByFoo();
}
