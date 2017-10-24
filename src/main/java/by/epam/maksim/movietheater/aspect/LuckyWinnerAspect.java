package by.epam.maksim.movietheater.aspect;

import by.epam.maksim.movietheater.entity.Ticket;
import by.epam.maksim.movietheater.entity.UserMessage;
import lombok.AllArgsConstructor;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Random;
import java.util.Set;

@Aspect
@Component
@AllArgsConstructor
public class LuckyWinnerAspect {

    private static final int PERCENTAGE_TO_BE_LUCKY = 95;

    private Random random;

    @Before("execution(* by.epam.maksim.movietheater.*.BookingService.bookTickets(..)) && args(tickets)")
    public void checkLuckyWinnerAndUpdatePrice(Set<Ticket> tickets) {
        int randomPercentage = random.nextInt(100);
        if (randomPercentage >= PERCENTAGE_TO_BE_LUCKY && !tickets.isEmpty()) {
            Ticket ticket = tickets.iterator().next();
            if (ticket.getUser() != null) {
                ticket.getUser().getMessages().add(UserMessage
                        .build(ticket.getUser(), "Dude, You are so lucky. You saved " + ticket.getSellingPrice()));
            }
            ticket.setSellingPrice(BigDecimal.ZERO);
        }
    }

}