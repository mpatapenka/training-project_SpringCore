package by.epam.maksim.movietheater.repository.impl.inmemory;

import by.epam.maksim.movietheater.domain.Event;
import by.epam.maksim.movietheater.repository.EventRepository;
import org.apache.commons.lang3.SerializationUtils;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class IMEventRepositoryImpl extends IMAbstractGenericRepository<Event> implements EventRepository {

    @Nullable
    @Override
    public Event getByName(@Nonnull String name) {
        return storage.values().stream()
                .filter(event -> name.equals(event.getName()))
                .map(SerializationUtils::clone)
                .findFirst().orElse(null);
    }

}