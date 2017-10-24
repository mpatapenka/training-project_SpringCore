package by.epam.maksim.movietheater.repository.inmemory;

import by.epam.maksim.movietheater.entity.Event;
import by.epam.maksim.movietheater.repository.EventRepository;
import org.apache.commons.lang3.SerializationUtils;
import org.springframework.stereotype.Repository;

@Repository
public class IMEventRepository extends IMGenericRepository<Event> implements EventRepository {

    @Override
    public Event getByName(String name) {
        return storage.values().stream()
                .filter(event -> name.equals(event.getName()))
                .map(SerializationUtils::clone)
                .findFirst().orElse(null);
    }

}