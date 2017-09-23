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
public class TenthTicketDiscountStrategyImpl implements DiscountStrategy {

    private static final int TICKETS_TO_GIVE_DISCOUNT = 10;

    private final byte discount;

    @Override
    public byte calculateDiscount(@Nullable User user, @Nonnull Event event, @Nonnull LocalDateTime airDateTime,
            long numberOfTickets) {
        // Check discount for unregistered user
        if ((user == null && numberOfTickets >= TICKETS_TO_GIVE_DISCOUNT)
                // Check discount for registered user
                || (user != null && (user.getTickets().size() + numberOfTickets) / TICKETS_TO_GIVE_DISCOUNT >= 1)) {
            return discount;
        }
        return DiscountService.NO_DISCOUNT;
    }

}