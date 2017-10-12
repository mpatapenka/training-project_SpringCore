package by.epam.maksim.movietheater.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.util.Random;

@Configuration
@ComponentScan(basePackages = "by.epam.maksim.movietheater.aspect")
public class AspectConfig {

    @Bean
    @Scope("prototype")
    public Random random() {
        return new Random();
    }

}