package edu.depaul.cdm.se452.cryptotradingsimulator.landing;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LandingController {
    @GetMapping()
    public ModelAndView showIndex() {
        ModelAndView mv = new ModelAndView("landing");
        return mv;
    }
}
