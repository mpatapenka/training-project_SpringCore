package by.epam.maksim.movietheater.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan(basePackages = "by.epam.maksim.movietheater.service.impl")
@Import({DiscountConfig.class})
public class ServiceConfig {
}