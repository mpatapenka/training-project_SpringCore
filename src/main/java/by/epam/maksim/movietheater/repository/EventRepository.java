package by.epam.maksim.movietheater.repository;

import by.epam.maksim.movietheater.entity.Event;

public interface EventRepository extends GenericRepository<Event> {
    Event getByName(String name);
}