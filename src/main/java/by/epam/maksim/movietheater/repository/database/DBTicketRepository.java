package by.epam.maksim.movietheater.repository.database;

import by.epam.maksim.movietheater.entity.Ticket;
import by.epam.maksim.movietheater.repository.TicketRepository;
import org.springframework.stereotype.Repository;

@Repository
public class DBTicketRepository extends DBGenericRepository<Ticket> implements TicketRepository {

    @Override
    Class<Ticket> getEntityClass() {
        return Ticket.class;
    }

}