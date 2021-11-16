package edu.depaul.cdm.se452.cryptotradingsimulator.ads;

import edu.depaul.cdm.se452.cryptotradingsimulator.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import java.util.List;
import java.util.HashMap;

@Controller
@RequestMapping("/ads")
public class AdsController {
    @Autowired
    private AdsRepository adsRepo;

    private Long mockUserId = 1L;

    private Date daysAgo(Long days) {
        return new Date(System.currentTimeMillis() - days * 24 * 3600 * 1000);
    }

    @GetMapping("")
    public String showAll(Model model, HttpServletRequest request) {
        Iterable<Ads> ads = adsRepo.findAll();
        model.addAttribute("ads", ads);
        return "ads/list";
    }

    @GetMapping(path = "/create")
    public String add(Model model) {
        model.addAttribute("ads", new Ads());
        return "ads/create";
    }

    @PostMapping
    public String saveAd(HttpServletRequest request, @ModelAttribute("ads") Ads newAd, BindingResult bindingResult, @RequestBody MultiValueMap<String, String> formData) {
        String startDate = formData.get("startDate").get(0);
        String endDate = formData.get("endDate").get(0);
        String companyId = formData.get("companyId").get(0);
        String logo = formData.get("logo").get(0);
        String message = formData.get("message").get(0);

        LocalDateTime now = LocalDateTime.now();
        newAd.setId(now.toString());
        newAd.setCompanyId(companyId);
        newAd.setStartDate(startDate);
        newAd.setEndDate(endDate);
        newAd.setPricePerAd(100.00);
        newAd.setMessage(message);
        newAd.setLogo(logo);
        newAd.setCreatedAt(now);
        adsRepo.save(newAd);
        return "redirect:/ads";
    }
}
