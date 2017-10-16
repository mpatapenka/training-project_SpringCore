package by.epam.maksim.movietheater.service.strategy.discount;

import by.epam.maksim.movietheater.config.TestAppConfig;
import by.epam.maksim.movietheater.domain.Event;
import by.epam.maksim.movietheater.domain.Ticket;
import by.epam.maksim.movietheater.domain.User;
import by.epam.maksim.movietheater.service.strategy.DiscountStrategy;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDateTime;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static by.epam.maksim.movietheater.service.DiscountService.FIFTY_PERCENTAGE_DISCOUNT;
import static by.epam.maksim.movietheater.service.DiscountService.NO_DISCOUNT;
import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestAppConfig.class)
public class TenthTicketDiscountStrategyTest {

    @Autowired
    @Qualifier("tenthTicketDiscountStrategy")
    private DiscountStrategy tenthTicketStrategy;

    @Test
    public void noDiscountInCaseNotRegisteredUserAndNotEnoughTickets() {
        assertEquals(NO_DISCOUNT, tenthTicketStrategy.calculateDiscount(null, new Event(), LocalDateTime.now(), 5));
    }

    @Test
    public void noDiscountIfUserDidntBuyEnoughTickets() {
        assertEquals(NO_DISCOUNT, tenthTicketStrategy.calculateDiscount(new User(), new Event(), LocalDateTime.now(), 8));
    }

    @Test
    public void discountInCaseNotRegisteredUserIfHeBoughtEnoughTickets() {
        assertEquals(FIFTY_PERCENTAGE_DISCOUNT, tenthTicketStrategy.calculateDiscount(null, new Event(), LocalDateTime.now(), 10));
    }

    @Test
    public void noDiscountInCaseRegisteredUserBoughtEnoughTickets() {
        User user = new User();
        user.getTickets().addAll(IntStream.range(0, 6)
                .mapToObj(i -> new Ticket(user, new Event(), LocalDateTime.now().plusDays(5), i))
                .collect(Collectors.toSet()));
        assertEquals(NO_DISCOUNT, tenthTicketStrategy.calculateDiscount(user, new Event(), LocalDateTime.now(), 5));
    }

    @Test
    public void noDiscountInCaseRegisteredUserBoughtNotEnoughTickets() {
        User user = new User();
        user.getTickets().addAll(IntStream.range(0, 6)
                .mapToObj(i -> new Ticket(user, new Event(), LocalDateTime.now().plusDays(5), i))
                .collect(Collectors.toSet()));
        assertEquals(NO_DISCOUNT, tenthTicketStrategy.calculateDiscount(user, new Event(), LocalDateTime.now(), 3));
    }

    @Test
    public void discountInCaseRegisteredUserBoughtExactTenTickets() {
        User user = new User();
        user.getTickets().addAll(IntStream.range(0, 9)
                .mapToObj(i -> new Ticket(user, new Event(), LocalDateTime.now().plusDays(5), i))
                .collect(Collectors.toSet()));
        assertEquals(FIFTY_PERCENTAGE_DISCOUNT, tenthTicketStrategy.calculateDiscount(user, new Event(), LocalDateTime.now(), 1));
    }

}