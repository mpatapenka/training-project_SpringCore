package by.epam.maksim.movietheater.aspect;

import by.epam.maksim.movietheater.domain.Ticket;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;

@Aspect
@Component
public class LuckyWinnerAspect {

    private static final int PERCENTAGE_TO_BE_LUCKY = 95;

    private final Random random = new Random();

    @Before("execution(* by.epam.maksim.movietheater.*.BookingService.bookTickets(..)) && args(tickets)")
    public void checkLuckyWinnerAndUpdatePrice(Set<Ticket> tickets) {
        int randomPercentage = random.nextInt(100);
        if (randomPercentage >= PERCENTAGE_TO_BE_LUCKY) {
            boolean isDone = false;
            for (Iterator<Ticket> iterator = tickets.iterator(); iterator.hasNext() && !isDone; ) {
                Ticket ticket = iterator.next();
                if (ticket.getUser() != null) {
                    ticket.getUser().getMessages().add("You are lucky");
                }
                ticket.setSellingPrice(BigDecimal.ZERO);
                isDone = random.nextBoolean();
            }
        }
    }

}