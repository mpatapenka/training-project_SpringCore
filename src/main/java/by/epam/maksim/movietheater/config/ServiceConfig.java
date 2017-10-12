package by.epam.maksim.movietheater.config;

import by.epam.maksim.movietheater.domain.Auditorium;
import by.epam.maksim.movietheater.util.ConfigUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.io.File;
import java.util.Map;

@Configuration
@ComponentScan(basePackages = "by.epam.maksim.movietheater.service")
@Import({RepositoryConfig.class, DiscountConfig.class})
public class ServiceConfig {

    @Value("${auditoriums.dir}")
    private String auditoriumsDirectory;

    @Bean
    public Map<String, Auditorium> auditoriumsStorage() {
        return ConfigUtils.parseAuditoriums(ConfigUtils.readAllProperties(new File(auditoriumsDirectory)));
    }

}