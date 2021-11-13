package edu.depaul.cdm.se452.cryptotradingsimulator.userAuth;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import edu.depaul.cdm.se452.cryptotradingsimulator.UserAuthentication;
import edu.depaul.cdm.se452.cryptotradingsimulator.UserAuthenticationRepository;

@Controller
@RequestMapping("/home")
public class userAuthController {

    @Autowired
    private UserAuthenticationRepository repository;

    @GetMapping("")
    public String viewHomePage() {
        return "userAuths/index";
    }

    @GetMapping("/register")
    public String showSignUpForm(Model model) {
        model.addAttribute("UserAuthentication", new UserAuthentication());
        return "userAuths/signup_form";
    }

    @PostMapping("/process_register")
    public String processRegistration(UserAuthentication userAuth) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encodedPassword = encoder.encode(userAuth.getPassword());
        userAuth.setPassword(encodedPassword);
        repository.save(userAuth);
        // System.out.println(repository.findAll());

        return "userAuths/register_success";
    }

    @GetMapping("/list_users")
    public String viewUsersList(Model model) {
        List<UserAuthentication> listUsers = repository.findAll();
        model.addAttribute("listUsers", listUsers);

        return "userAuths/users";
    }

}
