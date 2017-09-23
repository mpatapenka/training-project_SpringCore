package by.epam.maksim.movietheater.service.strategy.discount;

import by.epam.maksim.movietheater.domain.Event;
import by.epam.maksim.movietheater.domain.User;
import by.epam.maksim.movietheater.service.DiscountService;
import by.epam.maksim.movietheater.service.strategy.DiscountStrategy;
import lombok.AllArgsConstructor;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.time.LocalDateTime;

@AllArgsConstructor
public class BirthdayDiscountStrategyImpl implements DiscountStrategy {

    private final long daysWithinAirDate;
    private final byte discount;

    @Override
    public byte calculateDiscount(@Nullable User user, @Nonnull Event event, @Nonnull LocalDateTime airDateTime,
            long numberOfTickets) {
        if (user != null && user.getBirthday() != null
                && user.getBirthday().isAfter(airDateTime.minusDays(daysWithinAirDate).toLocalDate())
                && user.getBirthday().isBefore(airDateTime.plusDays(daysWithinAirDate).toLocalDate())) {
            return discount;
        }
        return DiscountService.NO_DISCOUNT;
    }
}