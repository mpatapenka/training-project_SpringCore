package by.epam.maksim.movietheater.config;

import by.epam.maksim.movietheater.domain.Event;
import by.epam.maksim.movietheater.repository.TicketRepository;
import by.epam.maksim.movietheater.service.BookingService;
import by.epam.maksim.movietheater.service.CounterService;
import by.epam.maksim.movietheater.service.DiscountService;
import by.epam.maksim.movietheater.service.UserService;
import by.epam.maksim.movietheater.service.impl.BookingServiceImpl;
import by.epam.maksim.movietheater.service.impl.DiscountServiceImpl;
import by.epam.maksim.movietheater.service.strategy.DiscountStrategy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;

import java.util.Arrays;

import static org.mockito.Mockito.mock;

@Configuration
@EnableAspectJAutoProxy
@ComponentScan(basePackages = {"by.epam.maksim.movietheater.service.strategy.discount", "by.epam.maksim.movietheater.aspect"})
@PropertySource({"classpath:properties/app.properties"})
public class TestAppConfig {

    @Value("${event.highrated.multiplier}") private double multiplierForHighRatedEvents;
    @Value("${seats.vip.multiplier}") private double multiplierForVipSeats;

    @Bean
    public DiscountStrategy mockDiscountStrategy1() {
        return mock(DiscountStrategy.class);
    }

    @Bean
    public DiscountStrategy mockDiscountStrategy2() {
        return mock(DiscountStrategy.class);
    }

    @Bean
    public DiscountService discountService() {
        return new DiscountServiceImpl(Arrays.asList(mockDiscountStrategy1(), mockDiscountStrategy2()));
    }

    @Bean
    public TicketRepository mockTicketRepository() {
        return mock(TicketRepository.class);
    }

    @Bean
    public DiscountService mockDiscountService() {
        return mock(DiscountService.class);
    }

    @Bean
    public UserService mockUserService() {
        return mock(UserService.class);
    }

    @Bean
    public BookingService bookingService() {
        return new BookingServiceImpl(mockTicketRepository(), mockDiscountService(), mockUserService(), multiplierForHighRatedEvents,
                multiplierForVipSeats);
    }

    @Bean
    @Scope("prototype")
    public Event event() {
        return new Event();
    }

    @Bean
    public CounterService mockCounterService() {
        return mock(CounterService.class);
    }

}