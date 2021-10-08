package edu.depaul.cdm.se452.cryptotradingsimulator;

import java.util.Scanner;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

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
