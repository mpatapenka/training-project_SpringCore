package by.epam.maksim.movietheater.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;

@Configuration
@EnableAspectJAutoProxy
@PropertySource({"classpath:properties/app.properties"})
@Import({RepositoryConfig.class, ServiceConfig.class, AspectConfig.class})
public class AppConfig {
}