package edu.depaul.cdm.se452.cryptotradingsimulator.userAuth;

import edu.depaul.cdm.se452.cryptotradingsimulator.AdminMetric;
import edu.depaul.cdm.se452.cryptotradingsimulator.AdminMetricRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;

@Component
public class CustomSessionListener implements ApplicationListener {
    @Autowired
    private AdminMetricRepository repo;

    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        if (event instanceof AuthenticationSuccessEvent)
        {
            AdminMetric m = new AdminMetric();
            m.setName("sign_in");
            m.setCreatedAt(LocalDateTime.now());
            repo.save(m);
        }
    }
}