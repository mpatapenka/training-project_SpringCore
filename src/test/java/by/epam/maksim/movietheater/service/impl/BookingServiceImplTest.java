package by.epam.maksim.movietheater.service.impl;

import by.epam.maksim.movietheater.config.TestAppConfig;
import by.epam.maksim.movietheater.entity.Auditorium;
import by.epam.maksim.movietheater.entity.Event;
import by.epam.maksim.movietheater.entity.EventRating;
import by.epam.maksim.movietheater.entity.Seance;
import by.epam.maksim.movietheater.entity.Ticket;
import by.epam.maksim.movietheater.entity.User;
import by.epam.maksim.movietheater.repository.TicketRepository;
import by.epam.maksim.movietheater.service.BookingService;
import by.epam.maksim.movietheater.service.DiscountService;
import by.epam.maksim.movietheater.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.internal.util.collections.Sets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Set;

import static by.epam.maksim.movietheater.service.DiscountService.FIFTY_PERCENTAGE_DISCOUNT;
import static by.epam.maksim.movietheater.service.DiscountService.NO_DISCOUNT;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyCollectionOf;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestAppConfig.class)
public class BookingServiceImplTest {

    private static final BigDecimal basePrice = new BigDecimal(100);
    private static final Auditorium mockAuditorium = mock(Auditorium.class);

    @Autowired
    private BookingService bookingService;

    @Autowired
    private DiscountService mockDiscountService;

    @Autowired
    private TicketRepository mockTicketRepository;

    @Autowired
    private UserService mockUserService;

    @Before
    public void beforeTest() {
        reset(mockDiscountService);
        reset(mockTicketRepository);
        reset(mockUserService);
        reset(mockAuditorium);
    }

    @Test
    public void checkTotalPriceInCaseNoDiscountAndOnlyRegularSeatsAndNotHighRatingEvent() {
        doReturn(NO_DISCOUNT).when(mockDiscountService).getDiscount(anyObject(), anyObject(), anyObject(), anyInt());
        doReturn(0L).when(mockAuditorium).countVipSeats(anyCollectionOf(Long.class));

        LocalDateTime now = LocalDateTime.now();
        Event event = createStubEvent(now, EventRating.MID);
        Set<Long> seats = Sets.newSet(1L, 2L, 3L);

        assertTrue(new BigDecimal(300).compareTo(bookingService.getTicketsPrice(event, now, new User(), seats)) == 0);
    }

    @Test
    public void checkTotalPriceInCaseNoDiscountAndOnlyRegularSeatsAndHighRatingEvent() {
        doReturn(NO_DISCOUNT).when(mockDiscountService).getDiscount(anyObject(), anyObject(), anyObject(), anyInt());
        doReturn(0L).when(mockAuditorium).countVipSeats(anyCollectionOf(Long.class));

        LocalDateTime now = LocalDateTime.now();
        Event event = createStubEvent(now, EventRating.HIGH);
        Set<Long> seats = Sets.newSet(1L, 2L, 3L);

        assertTrue(new BigDecimal(360).compareTo(bookingService.getTicketsPrice(event, now, new User(), seats)) == 0);
    }

    @Test
    public void checkTotalPriceInCaseDiscountAndOnlyRegularSeatsAndNotHighRatingEvent() {
        doReturn(FIFTY_PERCENTAGE_DISCOUNT).when(mockDiscountService).getDiscount(anyObject(), anyObject(), anyObject(), anyInt());
        doReturn(0L).when(mockAuditorium).countVipSeats(anyCollectionOf(Long.class));

        LocalDateTime now = LocalDateTime.now();
        Event event = createStubEvent(now, EventRating.LOW);
        Set<Long> seats = Sets.newSet(1L, 2L, 3L);

        assertTrue(new BigDecimal(150).compareTo(bookingService.getTicketsPrice(event, now, new User(), seats)) == 0);
    }

    @Test
    public void checkTotalPriceInCaseDiscountAndOnlyRegularSeatsAndHighRatingEvent() {
        doReturn(FIFTY_PERCENTAGE_DISCOUNT).when(mockDiscountService).getDiscount(anyObject(), anyObject(), anyObject(), anyInt());
        doReturn(0L).when(mockAuditorium).countVipSeats(anyCollectionOf(Long.class));

        LocalDateTime now = LocalDateTime.now();
        Event event = createStubEvent(now, EventRating.HIGH);
        Set<Long> seats = Sets.newSet(1L, 2L, 3L);

        assertTrue(new BigDecimal(180).compareTo(bookingService.getTicketsPrice(event, now, new User(), seats)) == 0);
    }

    @Test
    public void checkTotalPriceInCaseDiscountAndVipAndRegularSeatsAndHighRatingEvent() {
        doReturn(FIFTY_PERCENTAGE_DISCOUNT).when(mockDiscountService).getDiscount(anyObject(), anyObject(), anyObject(), anyInt());
        doReturn(1L).when(mockAuditorium).countVipSeats(Collections.singleton(1L));

        LocalDateTime now = LocalDateTime.now();
        Event event = createStubEvent(now, EventRating.HIGH);
        Set<Long> seats = Sets.newSet(1L, 2L, 3L);

        assertTrue(new BigDecimal(240).compareTo(bookingService.getTicketsPrice(event, now, new User(), seats)) == 0);
    }

    @Test
    public void checkThatBookTicketsIsStoreNeededInformationForUnregisteredUser() {
        Set<Ticket> tickets = Sets.newSet(
                Ticket.build(null, new Event(), LocalDateTime.now(), 13),
                Ticket.build(null, new Event(), LocalDateTime.now(), 14),
                Ticket.build(null, new Event(), LocalDateTime.now(), 15)
        );

        bookingService.bookTickets(tickets);

        verify(mockTicketRepository, times(tickets.size())).save(any());
    }

    @Test
    public void checkThatBookTicketsIsStoreNeededInformationForRegisteredUser() {
        User user = new User();
        Set<Ticket> tickets = Sets.newSet(
                Ticket.build(user, new Event(), LocalDateTime.now(), 13),
                Ticket.build(user, new Event(), LocalDateTime.now(), 14),
                Ticket.build(null, new Event(), LocalDateTime.now(), 15)
        );

        bookingService.bookTickets(tickets);

        verify(mockUserService, times(2)).save(any());
        verify(mockTicketRepository, times(tickets.size())).save(any());
    }

    @Test
    public void checkPurchasedTicketsForEvent() {
        LocalDateTime airDate = LocalDateTime.now();
        Event event = createStubEvent(airDate, EventRating.MID);
        Set<Ticket> stubTickets = Sets.newSet(
                Ticket.build(null, event, airDate, 13),
                Ticket.build(null, event, airDate, 14),
                Ticket.build(null, event, airDate.plusDays(3), 15),
                Ticket.build(null, event, airDate.plusDays(1), 15),
                Ticket.build(null, event, airDate.minusDays(3), 15),
                Ticket.build(null, new Event(), airDate, 16),
                Ticket.build(null, new Event(), airDate, 16),
                Ticket.build(null, new Event(), airDate, 16),
                Ticket.build(null, event, airDate, 17)
        );

        doReturn(stubTickets).when(mockTicketRepository).getAll();

        Set<Ticket> tickets = bookingService.getPurchasedTicketsForEvent(event, airDate);

        assertEquals(3, tickets.size());
    }

    private Event createStubEvent(LocalDateTime airDateTime, EventRating rating) {
        Seance seance = new Seance();
        seance.setAirDateTime(airDateTime);
        seance.setAuditorium(mockAuditorium);

        Event event = new Event();
        event.setBasePrice(basePrice);
        event.setRating(rating);
        event.getSeances().add(seance);
        return event;
    }

}