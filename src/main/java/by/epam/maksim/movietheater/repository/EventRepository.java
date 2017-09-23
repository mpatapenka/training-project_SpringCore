package by.epam.maksim.movietheater.repository;

import by.epam.maksim.movietheater.domain.Event;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public interface EventRepository extends GenericRepository<Event> {
    /**
     * Finding event by name.
     *
     * @param name Name of the event.
     * @return found event or <code>null</code>.
     */
    @Nullable
    Event getByName(@Nonnull String name);
}