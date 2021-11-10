package edu.depaul.cdm.se452.cryptotradingsimulator.admin;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import edu.depaul.cdm.se452.cryptotradingsimulator.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin/metrics")
public class MetricsController {
    @Autowired
    private AdminMetricRepository repo;

    private Long mockUserId = 1L;

    private Date daysAgo(Long days) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm-06:00");
        return new Date(System.currentTimeMillis() - days * 24 * 3600 * 1000);
    }

    private String toIso(Date d) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm-06:00");
        return df.format(d);
    }

    private Date parseIsoDate(String isoDate) {
        TemporalAccessor ta = DateTimeFormatter.ISO_INSTANT.parse(isoDate);
        Instant i = Instant.from(ta);
        return Date.from(i);
    }

    @GetMapping()
    public String showAll(Model model,
                          @RequestParam(required = false) String active,
                          @RequestParam(required = false) String metric,
                          @RequestParam(required = false) String timestamp) {
        ObjectMapper mapper = JsonMapper.builder()
                .addModule(new Jdk8Module())
                .addModule(new JavaTimeModule())
                .build();

        String activeFilter = active == null ? "week" : active;
        String activeMetric = metric == null ? "sign_in" : metric;

        Date parseDate;
        switch (activeFilter) {
            case "week":
                parseDate = daysAgo(7L);
                break;
            case "biweek":
                parseDate = daysAgo(14L);
                break;
            case "month":
                parseDate = daysAgo(31L);
                break;
            default:
                parseDate = daysAgo(7L);
        }
        List<AdminMetric> metricCountResult = repo.findAllWithCreatedAtAfter(
                LocalDateTime.of(parseDate.getYear(), parseDate.getMonth(), parseDate.getDay() + 1, 0, 0)
        ).stream().filter(x -> x.getName().equals(activeMetric)).collect(Collectors.toList());

        HashMap<LocalDate, List<AdminMetric>> groupedResults = AdminMetric.groupByDay(metricCountResult);

        model.addAttribute("week_ts", toIso(daysAgo(7L)));
        model.addAttribute("biweekly_ts", toIso(daysAgo(14L)));
        model.addAttribute("month_ts", toIso(daysAgo(31L)));
        model.addAttribute("active", activeFilter);
        System.out.println("activeMetric = " + activeMetric);
        model.addAttribute("metric", activeMetric);

        try {
            String json = mapper.writeValueAsString(groupedResults);
            model.addAttribute("metrics", json);
            return "admin/metrics";
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return null;
    }
}
