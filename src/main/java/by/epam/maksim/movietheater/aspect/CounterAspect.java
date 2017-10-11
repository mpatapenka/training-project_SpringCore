package by.epam.maksim.movietheater.aspect;

import by.epam.maksim.movietheater.domain.Event;
import by.epam.maksim.movietheater.domain.Ticket;
import by.epam.maksim.movietheater.service.CounterService;
import lombok.AllArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;

import static by.epam.maksim.movietheater.service.CounterService.BOOK_TICKET_FOR_EVENT_DOMAIN;
import static by.epam.maksim.movietheater.service.CounterService.GET_BASE_PRICE_DOMAIN;
import static by.epam.maksim.movietheater.service.CounterService.GET_NAME_DOMAIN;

@Aspect
@Component
@AllArgsConstructor
public class CounterAspect {

    @Autowired
    private CounterService counterService;

    @Pointcut("within(by.epam.maksim.movietheater.*.Event)")
    private void eventClass() { }

    @AfterReturning(value = "execution(* *.getName(..)) && eventClass()", returning = "name")
    public void countEventGetNameCalls(String name) {
        counterService.increment(Event.class, name, GET_NAME_DOMAIN);
    }

    @Before("execution(* *.getBasePrice(..)) && eventClass()")
    public void countEventGetBasePriceCalls(JoinPoint joinPoint) {
        counterService.increment(Event.class, ((Event) joinPoint.getTarget()).getName(), GET_BASE_PRICE_DOMAIN);
    }

    @Before("execution(* by.epam.maksim.movietheater.*.BookingService.bookTickets(..)) && args(tickets)")
    public void countHowManyTicketsWereBookedForEvent(Set<Ticket> tickets) {
        for (Ticket ticket : tickets) {
            Event event = ticket.getEvent();
            if (event != null) {
                counterService.increment(Event.class, event.getName(), BOOK_TICKET_FOR_EVENT_DOMAIN);
            }
        }
    }

}