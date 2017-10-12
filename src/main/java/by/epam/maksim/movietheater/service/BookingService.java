package by.epam.maksim.movietheater.service;

import by.epam.maksim.movietheater.domain.Event;
import by.epam.maksim.movietheater.domain.Ticket;
import by.epam.maksim.movietheater.domain.User;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

public interface BookingService {
    BigDecimal getTicketsPrice(Event event, LocalDateTime dateTime, User user, Set<Long> seats);
    void bookTickets(Set<Ticket> tickets);
    Set<Ticket> getPurchasedTicketsForEvent(Event event, LocalDateTime dateTime);
}