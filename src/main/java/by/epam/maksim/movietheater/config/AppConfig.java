package by.epam.maksim.movietheater.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;

@Configuration
@EnableAspectJAutoProxy
@PropertySource({"classpath:properties/app.properties"})
@ComponentScan("by.epam.maksim.movietheater.listener")
@Import({RepositoryConfig.class, ServiceConfig.class, AspectConfig.class, WebConfig.class})
public class AppConfig {
}