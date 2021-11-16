package edu.depaul.cdm.se452.cryptotradingsimulator;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import javax.persistence.*;
import edu.depaul.cdm.se452.cryptotradingsimulator.userAuth.Authority;
import lombok.Data;
import static edu.depaul.cdm.se452.cryptotradingsimulator.userAuth.AuthorityType.ROLE_ADMIN;

@Data
@Entity
@Table(name = "user_auth_sql")
public class UserAuthentication {
    @Id
    @GeneratedValue
    private long id;

    @Column
    private String username;

    @Column
    private String password;

    @ManyToMany
    @JoinTable(name = "users_authority", joinColumns = { @JoinColumn(name = "user_id") }, inverseJoinColumns = {
            @JoinColumn(name = "authority_id") })
    private Set<Authority> authorities = new HashSet<>();

    public Boolean isAdmin() {
        return this.authorities.stream().filter(x -> x.getName().equals(ROLE_ADMIN)).collect(Collectors.toList())
                .size() > 0;
    }

}
