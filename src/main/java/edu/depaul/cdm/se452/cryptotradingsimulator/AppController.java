package edu.depaul.cdm.se452.cryptotradingsimulator;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AppController {
    @GetMapping("/")
    public String index() {
        return "Greetings from Spring Boot!!";
    }
}