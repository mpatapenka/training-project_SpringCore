package by.epam.maksim.movietheater.aspect;

import by.epam.maksim.movietheater.config.TestAppConfig;
import by.epam.maksim.movietheater.domain.Event;
import by.epam.maksim.movietheater.domain.Ticket;
import by.epam.maksim.movietheater.service.BookingService;
import by.epam.maksim.movietheater.service.CounterService;
import com.google.common.collect.Sets;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

import static by.epam.maksim.movietheater.service.CounterService.BOOK_TICKET_FOR_EVENT_DOMAIN;
import static by.epam.maksim.movietheater.service.CounterService.GET_BASE_PRICE_DOMAIN;
import static by.epam.maksim.movietheater.service.CounterService.GET_NAME_DOMAIN;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestAppConfig.class)
public class CounterAspectTest {

    @Autowired
    private ObjectFactory<Event> eventFactory;

    @Autowired
    private CounterService mockCounterService;

    @Autowired
    private BookingService bookingService;

    @Test
    public void checkHowManyTimesEventGetNameCalled() {
        Event event1 = buildEvent("event1");
        Event event2 = buildEvent("event2");
        Event event3 = buildEvent("event3");


        event1.getName();
        event1.getName();

        event2.getName();

        event3.getName();
        event3.getName();
        event3.getName();


        verify(mockCounterService, times(2)).increment(Event.class, "event1", GET_NAME_DOMAIN);
        verify(mockCounterService, times(1)).increment(Event.class, "event2", GET_NAME_DOMAIN);
        verify(mockCounterService, times(3)).increment(Event.class, "event3", GET_NAME_DOMAIN);
    }

    @Test
    public void checkHowManyTimesEventGetBasePriceCalled() {
        Event event1 = buildEvent("event1");
        Event event2 = buildEvent("event2");
        Event event3 = buildEvent("event3");


        event1.getBasePrice();
        event1.getBasePrice();

        event2.getBasePrice();

        event3.getBasePrice();
        event3.getBasePrice();
        event3.getBasePrice();


        verify(mockCounterService, times(2)).increment(Event.class, "event1", GET_BASE_PRICE_DOMAIN);
        verify(mockCounterService, times(1)).increment(Event.class, "event2", GET_BASE_PRICE_DOMAIN);
        verify(mockCounterService, times(3)).increment(Event.class, "event3", GET_BASE_PRICE_DOMAIN);
    }

    @Test
    public void checkHowManyTimesTicketsWereBookedForEvent() {
        Event event1 = buildEvent("event1");
        Event event2 = buildEvent("event2");
        Event event3 = buildEvent("event3");

        Set<Ticket> ticketsToBook = Sets.newHashSet(
                new Ticket(null, event1, LocalDateTime.now(), 1),
                new Ticket(null, event1, LocalDateTime.now(), 2),
                new Ticket(null, event2, LocalDateTime.now(), 3),
                new Ticket(null, event3, LocalDateTime.now(), 4),
                new Ticket(null, event3, LocalDateTime.now(), 5),
                new Ticket(null, event3, LocalDateTime.now(), 6)
        );

        bookingService.bookTickets(ticketsToBook);

        verify(mockCounterService, times(2)).increment(Event.class, "event1", BOOK_TICKET_FOR_EVENT_DOMAIN);
        verify(mockCounterService, times(1)).increment(Event.class, "event2", BOOK_TICKET_FOR_EVENT_DOMAIN);
        verify(mockCounterService, times(3)).increment(Event.class, "event3", BOOK_TICKET_FOR_EVENT_DOMAIN);
    }

    private Event buildEvent(String name) {
        Event event = eventFactory.getObject();
        event.setName(name);
        event.setBasePrice(BigDecimal.TEN);
        return event;
    }

}