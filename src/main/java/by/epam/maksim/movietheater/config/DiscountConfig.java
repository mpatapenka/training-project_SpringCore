package by.epam.maksim.movietheater.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "by.epam.maksim.movietheater.service.strategy.discount")
public class DiscountConfig {
}