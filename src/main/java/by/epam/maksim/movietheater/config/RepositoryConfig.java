package by.epam.maksim.movietheater.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "by.epam.maksim.movietheater.repository.inmemory")
public class RepositoryConfig {
}