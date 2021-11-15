package edu.depaul.cdm.se452.cryptotradingsimulator.userAuth;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Authority {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Enumerated(EnumType.STRING)
    private AuthorityType name;
}
