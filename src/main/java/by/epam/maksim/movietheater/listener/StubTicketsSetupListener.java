package by.epam.maksim.movietheater.listener;

import by.epam.maksim.movietheater.entity.Event;
import by.epam.maksim.movietheater.entity.EventRating;
import by.epam.maksim.movietheater.entity.Ticket;
import by.epam.maksim.movietheater.entity.User;
import by.epam.maksim.movietheater.repository.EventRepository;
import by.epam.maksim.movietheater.repository.TicketRepository;
import by.epam.maksim.movietheater.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Month;

@Component
@AllArgsConstructor
public class StubTicketsSetupListener implements ApplicationListener<ContextRefreshedEvent> {

    private final TicketRepository ticketRepository;
    private final UserRepository userRepository;
    private final EventRepository eventRepository;

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        if (ticketRepository.getAll().isEmpty()) {
            User user = new User();
            user.setFirstName("tempFirst");
            user.setLastName("tempLast");
            user.setEmail("tempEmail");

            userRepository.save(user);

            Event event = new Event();
            event.setName("tempEvent");
            event.setRating(EventRating.MID);
            event.setBasePrice(new BigDecimal("150"));

            eventRepository.save(event);

            ticketRepository.save(Ticket.build(user, event, LocalDateTime.of(2018, Month.MAY, 3, 15, 30),
                    15, new BigDecimal("200")));
        }
    }

}