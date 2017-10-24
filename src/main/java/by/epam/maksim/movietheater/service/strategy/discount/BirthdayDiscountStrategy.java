package by.epam.maksim.movietheater.service.strategy.discount;

import by.epam.maksim.movietheater.annotation.DiscountStrategyQualifier;
import by.epam.maksim.movietheater.entity.Event;
import by.epam.maksim.movietheater.entity.User;
import by.epam.maksim.movietheater.service.DiscountService;
import by.epam.maksim.movietheater.service.strategy.DiscountStrategy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@DiscountStrategyQualifier
public class BirthdayDiscountStrategy implements DiscountStrategy {

    private final long daysWithinAirDate;
    private final byte discount;

    public BirthdayDiscountStrategy(@Value("${strategy.birthday.dayswithinairdate}") long daysWithinAirDate,
            @Value("${strategy.birthday.discount}") byte discount) {
        this.daysWithinAirDate = daysWithinAirDate;
        this.discount = discount;
    }

    @Override
    public byte calculateDiscount(User user, Event event, LocalDateTime airDateTime, int numberOfTickets) {
        if (user != null && user.getBirthday() != null
                && user.getBirthday().isAfter(airDateTime.minusDays(daysWithinAirDate).toLocalDate())
                && user.getBirthday().isBefore(airDateTime.plusDays(daysWithinAirDate).toLocalDate())) {
            return discount;
        }
        return DiscountService.NO_DISCOUNT;
    }

}