package by.epam.maksim.movietheater.listener;

import by.epam.maksim.movietheater.service.AuditoriumService;
import by.epam.maksim.movietheater.util.ConfigUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.nio.file.Paths;

@Component
public class AuditoriumSetupListener implements ApplicationListener<ContextRefreshedEvent> {

    @Value("${auditoriums.dir}")
    private String auditoriumsDirectory;

    @Autowired
    private AuditoriumService auditoriumService;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (auditoriumService.getAll().isEmpty()) {
            ConfigUtils.parseAuditoriums(ConfigUtils.readAllProperties(Paths.get(auditoriumsDirectory)))
                    .values().forEach(a -> auditoriumService.save(a));
        }
    }

}