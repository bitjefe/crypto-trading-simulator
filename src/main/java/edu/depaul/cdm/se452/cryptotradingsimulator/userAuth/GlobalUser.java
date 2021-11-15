package edu.depaul.cdm.se452.cryptotradingsimulator.userAuth;

import edu.depaul.cdm.se452.cryptotradingsimulator.UserAuthenticationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class GlobalUser {

    @Autowired
    private UserAuthenticationRepository userAuthRepo;

    @ModelAttribute("isAdmin")
    public Boolean isAdmin() {
        Object authentication = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (authentication instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication;
            return userAuthRepo.findByUsername(userDetails.getUsername()).isAdmin();
        }
        return false;
    }
}
