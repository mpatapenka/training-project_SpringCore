package by.epam.maksim.movietheater.service.strategy;

import by.epam.maksim.movietheater.domain.Event;
import by.epam.maksim.movietheater.domain.User;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.time.LocalDateTime;

public interface DiscountStrategy {
    byte calculateDiscount(@Nullable User user, @Nonnull Event event, @Nonnull LocalDateTime airDateTime, long numberOfTickets);
}