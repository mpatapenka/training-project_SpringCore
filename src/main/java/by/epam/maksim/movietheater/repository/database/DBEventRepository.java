package by.epam.maksim.movietheater.repository.database;

import by.epam.maksim.movietheater.entity.Event;
import by.epam.maksim.movietheater.repository.EventRepository;
import org.springframework.stereotype.Repository;

@Repository
public class DBEventRepository extends DBGenericRepository<Event> implements EventRepository {

    @Override
    @SuppressWarnings("unchecked")
    public Event getByName(String name) {
        return (Event) sessionFactory.getCurrentSession()
                .createQuery("from " + entityName + " where _name = '" + name + "'")
                .getResultStream().findFirst().orElse(null);
    }

    @Override
    Class<Event> getEntityClass() {
        return Event.class;
    }

}