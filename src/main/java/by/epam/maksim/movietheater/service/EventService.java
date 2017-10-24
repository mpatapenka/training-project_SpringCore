package by.epam.maksim.movietheater.service;

import by.epam.maksim.movietheater.entity.Event;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

public interface EventService extends GenericService<Event> {
    Event getByName(String name);
    Set<Event> getForDateRange(LocalDate from, LocalDate to);
    Set<Event> getNextEvents(LocalDateTime to);
}