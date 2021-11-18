package edu.depaul.cdm.se452.cryptotradingsimulator.userInfo;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserInfoRepository extends MongoRepository<UserInfo, String> {
    UserInfo findByUserId(Long userId);
}