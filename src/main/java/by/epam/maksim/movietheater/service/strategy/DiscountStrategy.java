package by.epam.maksim.movietheater.service.strategy;

import by.epam.maksim.movietheater.domain.Event;
import by.epam.maksim.movietheater.domain.User;

import java.time.LocalDateTime;

public interface DiscountStrategy {
    byte calculateDiscount(User user, Event event, LocalDateTime airDateTime, int numberOfTicket);
}