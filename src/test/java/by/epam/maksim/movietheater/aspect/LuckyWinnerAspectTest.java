package by.epam.maksim.movietheater.aspect;

import by.epam.maksim.movietheater.config.TestAppConfig;
import by.epam.maksim.movietheater.entity.Event;
import by.epam.maksim.movietheater.entity.Ticket;
import by.epam.maksim.movietheater.entity.User;
import by.epam.maksim.movietheater.repository.TicketRepository;
import by.epam.maksim.movietheater.service.BookingService;
import com.google.common.collect.Sets;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Random;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestAppConfig.class)
public class LuckyWinnerAspectTest {

    @InjectMocks
    @Autowired
    private LuckyWinnerAspect luckyWinnerAspect;

    @Mock
    private Random mockRandom = mock(Random.class);

    @Autowired
    private TicketRepository mockTicketRepository;

    @Autowired
    private BookingService bookingService;

    @Captor
    private ArgumentCaptor<Ticket> ticketCaptor;

    @Before
    public void beforeTest() {
        reset(mockRandom);
        reset(mockTicketRepository);
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void checkThatUnLuckyTicketPriceNotChange() {
        doReturn(90).when(mockRandom).nextInt(anyInt());

        bookingService.bookTickets(Sets.newHashSet(Ticket.build(new User(), new Event(), LocalDateTime.now(), 13, BigDecimal.TEN)));

        verify(mockTicketRepository).save(ticketCaptor.capture());
        assertEquals(BigDecimal.TEN, ticketCaptor.getValue().getSellingPrice());
        assertTrue(ticketCaptor.getValue().getUser().getMessages().isEmpty());
    }

    @Test
    public void checkThatLuckyTicketPriceIsSetToZeroAndMessageAdded() {
        doReturn(99).when(mockRandom).nextInt(anyInt());

        bookingService.bookTickets(Sets.newHashSet(Ticket.build(new User(), new Event(), LocalDateTime.now(), 13, BigDecimal.TEN)));

        verify(mockTicketRepository).save(ticketCaptor.capture());
        assertEquals(BigDecimal.ZERO, ticketCaptor.getValue().getSellingPrice());
        assertEquals(1, ticketCaptor.getValue().getUser().getMessages().size());
    }

}