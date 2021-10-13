package edu.depaul.cdm.se452.cryptotradingsimulator;

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
    private String id;
    private String name;
    private String dateOfBirth;
    private String Email;
}