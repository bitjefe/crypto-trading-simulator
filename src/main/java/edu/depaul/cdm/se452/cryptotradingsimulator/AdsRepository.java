package edu.depaul.cdm.se452.cryptotradingsimulator;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface AdsRepository extends MongoRepository<Ads, String> {

}