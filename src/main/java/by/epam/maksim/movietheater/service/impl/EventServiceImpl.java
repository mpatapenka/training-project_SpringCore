package by.epam.maksim.movietheater.service.impl;

import by.epam.maksim.movietheater.domain.Event;
import by.epam.maksim.movietheater.repository.EventRepository;
import by.epam.maksim.movietheater.service.EventService;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

public class EventServiceImpl extends AbstractGenericService<Event, EventRepository> implements EventService {

    public EventServiceImpl(EventRepository repository) {
        super(repository);
    }

    @Nullable
    @Override
    public Event getByName(@Nonnull String name) {
        return repository.getByName(name);
    }

    @Nonnull
    @Override
    public Set<Event> getForDateRange(@Nonnull LocalDate from, @Nonnull LocalDate to) {
        return repository.getAll().stream()
                .filter(event -> event.getAirDates().higher(from.atStartOfDay()) != null)
                .filter(event -> event.getAirDates().lower(to.plusDays(1).atStartOfDay()) != null)
                .collect(Collectors.toSet());
    }

    @Nonnull
    @Override
    public Set<Event> getNextEvents(@Nonnull LocalDateTime to) {
        return repository.getAll().stream()
                .filter(event -> event.getAirDates().higher(LocalDateTime.now()) != null)
                .filter(event -> event.getAirDates().lower(to) != null)
                .collect(Collectors.toSet());
    }

}