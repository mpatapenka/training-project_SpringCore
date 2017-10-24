package by.epam.maksim.movietheater.repository.inmemory;

import by.epam.maksim.movietheater.entity.Ticket;
import by.epam.maksim.movietheater.repository.TicketRepository;
import org.springframework.stereotype.Repository;

@Repository
public class IMTicketRepository extends IMGenericRepository<Ticket> implements TicketRepository {
}