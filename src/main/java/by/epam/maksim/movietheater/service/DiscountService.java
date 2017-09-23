package by.epam.maksim.movietheater.service;

import by.epam.maksim.movietheater.domain.Event;
import by.epam.maksim.movietheater.domain.User;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.time.LocalDateTime;

public interface DiscountService {

    byte NO_DISCOUNT = 0;
    byte FIVE_PERCENTAGE_DISCOUNT = 5;
    byte FIFTY_PERCENTAGE_DISCOUNT = 50;

    /**
     * Getting discount based on some rules for user that buys some number of
     * tickets for the specific date time of the event.
     *
     * @param user            User that buys tickets. Can be <code>null</code>.
     * @param event           Event that tickets are bought for.
     * @param airDateTime     The date and time event will be aired.
     * @param numberOfTickets Number of tickets that user buys.
     * @return discount value from 0 to 100.
     */
    byte getDiscount(@Nullable User user, @Nonnull Event event, @Nonnull LocalDateTime airDateTime, long numberOfTickets);
}