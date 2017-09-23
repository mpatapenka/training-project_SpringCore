package by.epam.maksim.movietheater.service.impl;

import by.epam.maksim.movietheater.domain.Event;
import by.epam.maksim.movietheater.domain.User;
import by.epam.maksim.movietheater.service.DiscountService;
import by.epam.maksim.movietheater.service.strategy.DiscountStrategy;
import lombok.AllArgsConstructor;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.time.LocalDateTime;
import java.util.Collection;

@AllArgsConstructor
public class DiscountServiceImpl implements DiscountService {

    private final Collection<DiscountStrategy> discountStrategies;

    @Override
    public byte getDiscount(@Nullable User user, @Nonnull Event event, @Nonnull LocalDateTime airDateTime, long numberOfTickets) {
        return discountStrategies.stream()
                .map(ds -> ds.calculateDiscount(user, event, airDateTime, numberOfTickets))
                .max(Byte::compareTo).orElse(NO_DISCOUNT);
    }

}