package by.epam.maksim.movietheater.service.impl;

import by.epam.maksim.movietheater.domain.Event;
import by.epam.maksim.movietheater.domain.User;
import by.epam.maksim.movietheater.service.DiscountService;
import by.epam.maksim.movietheater.service.strategy.DiscountStrategy;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collection;

@Service
@AllArgsConstructor
public class DiscountServiceImpl implements DiscountService {

    private final Collection<DiscountStrategy> discountStrategies;

    @Override
    public byte getDiscount(User user, Event event, LocalDateTime airDateTime, int numberOfTicket) {
        return discountStrategies.stream()
                .map(ds -> ds.calculateDiscount(user, event, airDateTime, numberOfTicket))
                .max(Byte::compareTo).orElse(NO_DISCOUNT);
    }

}