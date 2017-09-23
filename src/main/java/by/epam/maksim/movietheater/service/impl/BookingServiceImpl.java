package by.epam.maksim.movietheater.service.impl;

import by.epam.maksim.movietheater.domain.Event;
import by.epam.maksim.movietheater.domain.EventRating;
import by.epam.maksim.movietheater.domain.Ticket;
import by.epam.maksim.movietheater.domain.User;
import by.epam.maksim.movietheater.repository.TicketRepository;
import by.epam.maksim.movietheater.service.BookingService;
import by.epam.maksim.movietheater.service.DiscountService;
import by.epam.maksim.movietheater.service.UserService;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

public class BookingServiceImpl extends AbstractGenericService<Ticket, TicketRepository> implements BookingService {

    private final DiscountService discountService;
    private final UserService userService;

    private final double multiplierForHighRatedEvents;
    private final double multiplierForVipSeats;

    public BookingServiceImpl(TicketRepository repository, DiscountService discountService, UserService userService,
            double multiplierForHighRatedEvents, double multiplierForVipSeats) {
        super(repository);
        this.discountService = discountService;
        this.userService = userService;
        this.multiplierForHighRatedEvents = multiplierForHighRatedEvents;
        this.multiplierForVipSeats = multiplierForVipSeats;
    }

    @Override
    public BigDecimal getTicketsPrice(@Nonnull Event event, @Nonnull LocalDateTime dateTime, @Nullable User user,
            @Nonnull Set<Long> seats) {
        BigDecimal basePrice = calculateBasePrice(event);
        long vipSeatsToPurchase = event.getAuditoriums().get(dateTime).countVipSeats(seats);
        long regularSeatsToPurchase = seats.size() - vipSeatsToPurchase;
        byte discount = discountService.getDiscount(user, event, dateTime, seats.size());

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
    public void bookTickets(@Nonnull Set<Ticket> tickets) {
        tickets.forEach(ticket -> {
            User user = ticket.getUser();
            if (user != null) {
                user.getTickets().add(ticket);
                userService.save(user);
            }
            save(ticket);
        });
    }

    @Nonnull
    @Override
    public Set<Ticket> getPurchasedTicketsForEvent(@Nonnull Event event, @Nonnull LocalDateTime dateTime) {
        return getAll().stream()
                .filter(ticket -> event.equals(ticket.getEvent()))
                .filter(ticket -> dateTime.isEqual(ticket.getDateTime()))
                .collect(Collectors.toSet());
    }

}