package edu.depaul.cdm.se452.cryptotradingsimulator.userInfo;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "users")
public class UserInfo {
    @Id
    private Long userId;
    private String userName;
    private String dateOfBirth;
    private String phone;
}