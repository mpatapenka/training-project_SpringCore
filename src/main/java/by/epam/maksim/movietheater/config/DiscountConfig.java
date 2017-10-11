package by.epam.maksim.movietheater.config;

import by.epam.maksim.movietheater.service.DiscountService;
import by.epam.maksim.movietheater.service.impl.DiscountServiceImpl;
import by.epam.maksim.movietheater.service.strategy.DiscountStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
public class DiscountConfig {

    @Autowired
    @Qualifier("birthdayDiscountStrategyImpl")
    private DiscountStrategy birthdayDiscountStrategy;

    @Autowired
    @Qualifier("tenthTicketDiscountStrategyImpl")
    private DiscountStrategy tenthTicketDiscountStrategy;

    @Bean
    public DiscountService discountService() {
        return new DiscountServiceImpl(Arrays.asList(birthdayDiscountStrategy, tenthTicketDiscountStrategy));
    }

}