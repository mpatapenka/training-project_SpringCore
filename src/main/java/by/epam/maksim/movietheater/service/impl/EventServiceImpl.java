package by.epam.maksim.movietheater.service.impl;

import by.epam.maksim.movietheater.domain.Event;
import by.epam.maksim.movietheater.repository.EventRepository;
import by.epam.maksim.movietheater.service.EventService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class EventServiceImpl extends AbstractGenericService<Event, EventRepository> implements EventService {

    public EventServiceImpl(EventRepository repository) {
        super(repository);
    }

    @Override
    public Event getByName(String name) {
        return repository.getByName(name);
    }

    @Override
    public Set<Event> getForDateRange(LocalDate from, LocalDate to) {
        return repository.getAll().stream()
                .filter(event -> event.getAirDates().higher(from.atStartOfDay()) != null)
                .filter(event -> event.getAirDates().lower(to.plusDays(1).atStartOfDay()) != null)
                .collect(Collectors.toSet());
    }

    @Override
    public Set<Event> getNextEvents(LocalDateTime to) {
        return repository.getAll().stream()
                .filter(event -> event.getAirDates().higher(LocalDateTime.now()) != null)
                .filter(event -> event.getAirDates().lower(to) != null)
                .collect(Collectors.toSet());
    }

}