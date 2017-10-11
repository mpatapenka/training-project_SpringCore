package by.epam.maksim.movietheater.service.strategy.discount;

import by.epam.maksim.movietheater.domain.Event;
import by.epam.maksim.movietheater.domain.User;
import by.epam.maksim.movietheater.service.DiscountService;
import by.epam.maksim.movietheater.service.strategy.DiscountStrategy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class TenthTicketDiscountStrategyImpl implements DiscountStrategy {

    private static final int TICKETS_TO_GIVE_DISCOUNT = 10;

    private final byte discount;

    public TenthTicketDiscountStrategyImpl(@Value("${strategy.tenthticket.discount}") byte discount) {
        this.discount = discount;
    }

    @Override
    public byte calculateDiscount(User user, Event event, LocalDateTime airDateTime, int numberOfTicket) {
        // Check discount for unregistered user
        if ((user == null && numberOfTicket >= TICKETS_TO_GIVE_DISCOUNT)
                // Check discount for registered user
                || (user != null && (user.getTickets().size() + numberOfTicket) / TICKETS_TO_GIVE_DISCOUNT >= 1
                        && (user.getTickets().size() + numberOfTicket) % TICKETS_TO_GIVE_DISCOUNT == 0)) {
            return discount;
        }
        return DiscountService.NO_DISCOUNT;
    }

}