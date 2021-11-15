package edu.depaul.cdm.se452.cryptotradingsimulator;

import java.util.HashSet;
import java.util.Scanner;
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
    @JoinTable(name = "users_authority",
            joinColumns = { @JoinColumn(name = "user_id") },
            inverseJoinColumns = { @JoinColumn(name = "authority_id") })
    private Set<Authority> authorities = new HashSet<>();

    public Boolean isAdmin() {
        return this.authorities.stream().filter(x -> x.getName().equals(ROLE_ADMIN)).collect(Collectors.toList()).size() > 0;
    }

    public boolean signIn(UserAuthenticationRepository repository) {
        System.out.println("Sign in here");

        Scanner inputScanner = new Scanner(System.in);
        String usernameInput = "";
        String passwordInput = "";

        System.out.print("Enter your username: ");
        usernameInput = inputScanner.nextLine();

        System.out.print("Enter your password: ");
        passwordInput = inputScanner.nextLine();

        for (UserAuthentication userA : repository.findAll()) {
            if (userA.username.equals(usernameInput) && userA.password.equals(passwordInput)) {
                return true;
            }

        }

        return false;

    }

    public void signUp(UserAuthenticationRepository repository) {

        Scanner inputScanner = new Scanner(System.in);
        String usernameInput = "";
        String passwordInput = "";
        String passwordRepeatInput = "";

        do {
            if (!passwordInput.equals(passwordRepeatInput)) {
                System.out.println("Passwords did not match, please try again!");
            }

            System.out.print("Enter a username: ");
            usernameInput = inputScanner.nextLine();

            System.out.print("Enter a password: ");
            passwordInput = inputScanner.nextLine();

            System.out.print("Enter the password again: ");
            passwordRepeatInput = inputScanner.nextLine();

        } while (!passwordInput.equals(passwordRepeatInput));

        System.out.println("Thank you for registring!.");
        System.out.println("You just logged in, welcome!");
        UserAuthentication userAuth = new UserAuthentication();
        userAuth.setUsername(usernameInput);
        userAuth.setPassword(passwordInput);
        repository.save(userAuth);

        inputScanner.close();

    }
}
