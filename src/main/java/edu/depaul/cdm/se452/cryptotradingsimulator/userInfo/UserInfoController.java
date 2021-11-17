package edu.depaul.cdm.se452.cryptotradingsimulator.userInfo;

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
@RequestMapping("/userInfo")
public class UserInfoController {
    @Autowired
    private UserInfoRepository userInfoRepo;

    @Autowired
    private UserAuthenticationRepository userAuthRepo;

    private Long mockUserId = 2L;

    private Date daysAgo(Long days) {
        return new Date(System.currentTimeMillis() - days * 24 * 3600 * 1000);
    }

    @GetMapping("")
    public String showAll(Model model, HttpServletRequest request) {
        Iterable<UserInfo> user = userInfoRepo.findByUserId(mockUserId);
        model.addAttribute("userInfo", user);
        return "userInfo/profile";
    }

}
