package edu.depaul.cdm.se452.cryptotradingsimulator;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface AdsRepository extends MongoRepository<Ads, String> {
}