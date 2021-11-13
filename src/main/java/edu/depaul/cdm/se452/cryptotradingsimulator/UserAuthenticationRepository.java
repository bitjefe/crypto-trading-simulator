package edu.depaul.cdm.se452.cryptotradingsimulator;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * For Pagination and Other JPA functionality beyond base CRUD services
 * 
 * @see https://docs.spring.io/spring-data/data-jpa/docs/current/api/org/springframework/data/jpa/repository/JpaRepository.html
 */
public interface UserAuthenticationRepository extends JpaRepository<UserAuthentication, Long> {
    @Query("SELECT u FROM UserAuthentication u WHERE u.username = ?1")
    UserAuthentication findByUsername(String username);

}
