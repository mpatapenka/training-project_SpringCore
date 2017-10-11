package by.epam.maksim.movietheater.repository.impl.inmemory;

import by.epam.maksim.movietheater.domain.Ticket;
import by.epam.maksim.movietheater.repository.TicketRepository;
import org.springframework.stereotype.Repository;

@Repository
public class IMTicketRepositoryImpl extends IMAbstractGenericRepository<Ticket> implements TicketRepository {
}