package by.epam.maksim.movietheater.service.impl;

import by.epam.maksim.movietheater.entity.Event;
import by.epam.maksim.movietheater.entity.EventRating;
import by.epam.maksim.movietheater.entity.Ticket;
import by.epam.maksim.movietheater.entity.User;
import by.epam.maksim.movietheater.repository.TicketRepository;
import by.epam.maksim.movietheater.service.BookingService;
import by.epam.maksim.movietheater.service.DiscountService;
import by.epam.maksim.movietheater.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class BookingServiceImpl extends AbstractGenericService<Ticket, TicketRepository> implements BookingService {

    private final DiscountService discountService;
    private final UserService userService;

    private final double multiplierForHighRatedEvents;
    private final double multiplierForVipSeats;

    @Autowired
    public BookingServiceImpl(DiscountService discountService, UserService userService,
            @Value("${event.highrated.multiplier}") double multiplierForHighRatedEvents,
            @Value("${seats.vip.multiplier}") double multiplierForVipSeats) {
        this.discountService = discountService;
        this.userService = userService;
        this.multiplierForHighRatedEvents = multiplierForHighRatedEvents;
        this.multiplierForVipSeats = multiplierForVipSeats;
    }

    @Override
    public BigDecimal getTicketsPrice(Event event, LocalDateTime dateTime, User user, Set<Long> seats) {
        int ticketNumber = 1;
        BigDecimal overallPrice = BigDecimal.ZERO;
        for (Long seat : seats) {
            overallPrice = overallPrice.add(getTicketPrice(event, dateTime, user, seat, ticketNumber++));
        }
        return overallPrice;
    }

    private BigDecimal getTicketPrice(Event event, LocalDateTime airDateTime, User user, long seat, int ticketNumber) {
        BigDecimal basePrice = calculateBasePrice(event);
        long vipSeatsToPurchase = event.getAuditorium(airDateTime).countVipSeats(Collections.singleton(seat));
        long regularSeatsToPurchase = 1 - vipSeatsToPurchase;
        byte discount = discountService.getDiscount(user, event, airDateTime, ticketNumber);

        BigDecimal overallPrice = calculateVipSeatsPrice(basePrice, vipSeatsToPurchase)
                .add(calculateRegularSeatsPrice(basePrice, regularSeatsToPurchase));

        return applyDiscount(overallPrice, discount);
    }

    private BigDecimal calculateBasePrice(Event event) {
        BigDecimal basePrice = event.getBasePrice();
        if (EventRating.HIGH.equals(event.getRating())) {
            basePrice = multiplyWithRounding(basePrice, multiplierForHighRatedEvents);
        }
        return basePrice;
    }

    private BigDecimal multiplyWithRounding(BigDecimal basePrice, double multiplier) {
        return basePrice.multiply(new BigDecimal(multiplier * 1000)).divide(new BigDecimal(1000), BigDecimal.ROUND_HALF_UP);
    }

    private BigDecimal calculateVipSeatsPrice(BigDecimal basePrice, long vipSeatsToPurchase) {
        return multiplyWithRounding(basePrice, multiplierForVipSeats).multiply(new BigDecimal(vipSeatsToPurchase));
    }

    private BigDecimal calculateRegularSeatsPrice(BigDecimal basePrice, long regularSeatsToPurchase) {
        return basePrice.multiply(new BigDecimal(regularSeatsToPurchase));
    }

    private BigDecimal applyDiscount(BigDecimal overallPrice, byte discount) {
        return overallPrice.multiply(new BigDecimal(100 - discount)).divide(new BigDecimal(100), BigDecimal.ROUND_HALF_UP);
    }

    @Override
    public void bookTickets(Set<Ticket> tickets) {
        tickets.forEach(ticket -> {
            User user = ticket.getUser();
            if (user != null) {
                user.getTickets().add(ticket);
                userService.save(user);
            }
            save(ticket);
        });
    }

    @Override
    public Set<Ticket> getPurchasedTicketsForEvent(Event event, LocalDateTime dateTime) {
        return getAll().stream()
                .filter(ticket -> event.equals(ticket.getEvent()))
                .filter(ticket -> dateTime.isEqual(ticket.getSeanceDateTime()))
                .collect(Collectors.toSet());
    }

}